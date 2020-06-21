package com.StagePFE.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.StagePFE.Storage.StorageService;
import com.StagePFE.dao.AnnonceRepository;
import com.StagePFE.dao.EtudiantAnnonceRepository;
import com.StagePFE.dao.EtudiantRepository;
import com.StagePFE.dao.RoleRepository;
import com.StagePFE.dao.UserRepository;
import com.StagePFE.entities.Annonce;
import com.StagePFE.entities.Etudiant;
import com.StagePFE.entities.EtudiantAnnonce;
import com.StagePFE.entities.Role;
import com.StagePFE.entities.User;


@Controller
public class EtudiantController {
	@Autowired
	private AnnonceRepository annonceRepository;
	@Autowired
	private EtudiantRepository etudiantRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private HttpServletRequest httpServletRequest;
	@Autowired
	private EtudiantAnnonceRepository etudiantAnnonceRepository;
	@Autowired
	private StorageService storageService;
	
	@PostMapping("/inscrireEtudiant")
	public RedirectView inscrireEtudiant(Model model, @ModelAttribute("etudiant") Etudiant e,
			@RequestParam(name="mdp") int mdp,
			@RequestParam(name="mdpConfirmation") int mdpConfirmation,
			@RequestParam("profile_photo") MultipartFile photo,
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(mdp!=mdpConfirmation) {
			model.addAttribute("errorMessage","confirmation invalide");
			return new RedirectView("/pageInscription");
		}
		
		/* upload photo*/
		if(photo.getSize()==0) {
			// photo par defaut
			e.setPhotoType("image/png");
			e.setPhotodata(Files.readAllBytes(Paths.get("src/main/resources/static/imgs/etudiant2.png")));
			
		}else {
			// photo de l'etudiant
			e.setPhotoType(photo.getContentType());
			e.setPhotodata(photo.getBytes());
		}
		
		e.setDateCreation(new Date());
		// get id
		e = etudiantRepository.save(e);
		
		e.setPhoto("PHOTO-ETUDIANT-ID"+e.getId());
		
		System.out.println(file.getSize());
		/* uplaod cv*/
		if(file.getSize()!=0) {  //  && cv.getContentType()
			e.setCvName("cv-ETUDIANT-ID"+e.getId()+".pdf");
			storageService.store(file,e.getCvName(),"cv");
		}
		
		e = etudiantRepository.save(e);
		
		BCryptPasswordEncoder bcp=new BCryptPasswordEncoder();
		User user=new User();
		user.setUsername(e.getEmail());user.setPassword(bcp.encode(Integer.toString(mdp)));user.setActive(true);
		user.addRole(roleRepository.findByRole("ETUDIANT"));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
		userRepository.save(user);
		
		return new RedirectView("/index");
	}
	
	
	@GetMapping("/postuler")
	public RedirectView postuler(RedirectAttributes attributes,
			@RequestParam(name="annonce" ) Long annonceId,
			@RequestParam(name="page" , defaultValue="0") int page,
			@RequestParam(name="motcle" , defaultValue="") String motcle,
			@RequestParam(name="localite" , defaultValue="") String lieu) {
		Annonce a = null;
		// get authenticated user
		User user = userRepository.findByUsername(httpServletRequest.getRemoteUser());
		if(user==null)new RedirectView("/login");
		
		// check if user is an "etudiant"
		List<Role> roles = user.getRoles();
		Role etudiantRole = new Role();
		etudiantRole.setRole("ETUDIANT");
		
		if(roles.contains(etudiantRole)) {
			
			// get authenticated "etudiant"
			Etudiant etudiant = etudiantRepository.findByEmail(user.getUsername()).get(0);
			
			//get selected "annonce"
			Optional<Annonce> result = annonceRepository.findById(annonceId);
			if (result.isPresent()) {
				a = result.get();
			}
			
			// register info about application
			EtudiantAnnonce etAnn = new EtudiantAnnonce();
			etAnn.setAnnonce(a);
			etAnn.setTypeRelation("postuler");
			etAnn.setDateCreation(new Date());
			etudiant.addEtudiantAnnonce(etAnn);
			etudiantRepository.save(etudiant);
			
			// get list of annonces
			
		}else {
			return new RedirectView("/login");
		}
		
		attributes.addAttribute("currentPage", page);
		attributes.addAttribute("motcle",motcle);
		attributes.addAttribute("localite",lieu);
		
		return new RedirectView("/index");
	}
	
	
	@GetMapping("/etudiantProfile")
	public String etudiantProfile(Model model,
			@RequestParam(name="page",defaultValue="0") int page
	) {
		boolean isAuthenticated = false;
		User user = userRepository.findByUsername(httpServletRequest.getRemoteUser());
		if(user==null) return "login";
		else {
			isAuthenticated = true;
		}
		
		Etudiant etudiant = etudiantRepository.findByEmail(httpServletRequest.getRemoteUser()).get(0);
		Page<EtudiantAnnonce> etudiantAnnonces = etudiantAnnonceRepository.findByEtudiant(etudiant, PageRequest.of(page, 6));
		
		model.addAttribute("etudiantAnnonces", etudiantAnnonces.getContent());
		model.addAttribute("etudiant",etudiant);
		model.addAttribute("isAuthenticated",isAuthenticated);
		model.addAttribute("isEntrepreneur",false);
		return "etudiantProfile";
	}
	
	@PostMapping("/modifierProfileEtudiant")
	public RedirectView modifierProfileEtudiant(Model model,
			@ModelAttribute("etudiant") Etudiant e,
			@RequestParam("file") MultipartFile file,
			@RequestParam(name="nouveaumdp") String nouveaumdp) throws IOException {
		User user = userRepository.findByUsername(httpServletRequest.getRemoteUser());
		if(user==null) return new RedirectView("/login");
		Etudiant etudiant = etudiantRepository.findByEmail(httpServletRequest.getRemoteUser()).get(0);
		etudiant.setAdresse(e.getAdresse());etudiant.setDescription(e.getDescription());etudiant.setEmail(e.getEmail());
		etudiant.setNom(e.getNom());etudiant.setPhoneNmbr(e.getPhoneNmbr());
		etudiant.setPrenom(e.getPrenom());//etudiant.setPhoto(e.getPhoto());
		
		/* uplaod cv*/
		if(file.getSize()!=0) {
			etudiant.setCvName("cv-ETUDIANT-ID"+etudiant.getId()+".pdf");
			storageService.store(file,etudiant.getCvName(),"cv");
		}
		etudiant = etudiantRepository.save(etudiant);

		
		if(!user.getUsername().equals(e.getEmail()) ) {
			user.setUsername(e.getEmail());
			System.out.println(user.getUsername());
			System.out.println(e.getEmail());
			userRepository.save(user);
			return new RedirectView("/login");
		}
		
		return new RedirectView("/profile");
	}
	
	
	@PostMapping("/photoEtudiant")
	public RedirectView photoEtudiant(Model model,
			@RequestParam("profile_photo") MultipartFile photo) throws IOException {
		User user = userRepository.findByUsername(httpServletRequest.getRemoteUser());
		if(user==null) return new RedirectView("/login");
		Etudiant etudiant = etudiantRepository.findByEmail(httpServletRequest.getRemoteUser()).get(0);
		
		if(photo.getSize()==0) {
			etudiant.setPhotoType("image/png");
			etudiant.setPhotodata(Files.readAllBytes(Paths.get("src/main/resources/static/imgs/etudiant2.png")));
		}else {
			etudiant.setPhotoType(photo.getContentType());
			etudiant.setPhotodata(photo.getBytes());
		}
		etudiant.setPhoto("PHOTO-ETUDIANT-ID"+etudiant.getId());
		
		etudiant = etudiantRepository.save(etudiant);
		
		return new RedirectView("/profile");
	}
}
