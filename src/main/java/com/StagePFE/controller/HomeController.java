package com.StagePFE.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import com.StagePFE.Storage.StorageService;
import com.StagePFE.dao.AnnonceRepository;
import com.StagePFE.dao.EntrepreneurRepository;
import com.StagePFE.dao.EtudiantAnnonceRepository;
import com.StagePFE.dao.EtudiantRepository;
import com.StagePFE.dao.ProfileRepository;
import com.StagePFE.dao.RoleRepository;
import com.StagePFE.dao.UserRepository;
import com.StagePFE.entities.Annonce;
import com.StagePFE.entities.Entrepreneur;
import com.StagePFE.entities.Etudiant;
import com.StagePFE.entities.EtudiantAnnonce;
import com.StagePFE.entities.Profile;
import com.StagePFE.entities.Role;
import com.StagePFE.entities.User;
import com.StagePFE.service.FileStorageService;

import aj.org.objectweb.asm.Attribute;
import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
@Controller
public class HomeController {
	@Autowired
	private AnnonceRepository annonceRepository;
	@Autowired
	private EntrepreneurRepository entrepreneurRepository;
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
	
	
	@GetMapping(value="/login")
	public String homePage(Model model) {
		model.addAttribute("isAuthenticated",false);
		model.addAttribute("isEntrepreneur",false);
 		return "login";
 	}
	
	
	@GetMapping("/logout")
	public String logout(Model model) {
		return "logout";
	}
	
	
	@GetMapping("/")
	public RedirectView redirectIndex() {
		return new RedirectView("/index");
	}
	
	
	@GetMapping("/index")
	public String index(Model model,
			@RequestParam(name="page" , defaultValue="0") int page,
			@RequestParam(name="motcle" , defaultValue="") String motcle,
			@RequestParam(name="localite" , defaultValue="") String lieu
			) {
		boolean isAuthenticated = false;
		List<Long> listannoncesId = new ArrayList<Long>();
		boolean isEntrepreneur = false;
		Etudiant etudiant = new Etudiant();
		Entrepreneur entrepreneur = new Entrepreneur();
		User user = userRepository.findByUsername(httpServletRequest.getRemoteUser());
		if(user!=null) {
			isAuthenticated = true;
			List<Role> roles = user.getRoles();
			
			// get authenticated entrepreneur 
			Role entrepreneurRole = new Role();
			entrepreneurRole.setRole("ENTREPRENEUR");
			
			if(roles.contains(entrepreneurRole)) {
				isEntrepreneur = true;
				entrepreneur = entrepreneurRepository.findByEmail(user.getUsername()).get(0);
			}
			
			
			Role etudiantRole = new Role();
			etudiantRole.setRole("ETUDIANT");
			
			if(roles.contains(etudiantRole)) {
				isEntrepreneur = false;
				etudiant = etudiantRepository.findByEmail(user.getUsername()).get(0);
				for(EtudiantAnnonce etudiantAnnonce : etudiant.getEtudiantAnnonces()) {
					listannoncesId.add(etudiantAnnonce.getAnnonce().getId());
				}
			}
			
		}
		
		
		Page<Annonce> annonces=annonceRepository.searchIndexPage("%"+motcle+"%","%"+lieu+"%", PageRequest.of(page, 9));
		model.addAttribute("annonces",annonces.getContent());
		model.addAttribute("pages", new int[annonces.getTotalPages()]);
		model.addAttribute("listannoncesId",listannoncesId);
		model.addAttribute("isAuthenticated",isAuthenticated);
		model.addAttribute("isEntrepreneur",isEntrepreneur);
		model.addAttribute("etudiant",etudiant);
		model.addAttribute("entrepreneur",entrepreneur);
		model.addAttribute("currentPage", page);
		model.addAttribute("motcle",motcle);
		model.addAttribute("localite",lieu);
		return "index";
	}
	
	@GetMapping("/index/filter")
	public String index(Model model,
			@RequestParam(name="page" , defaultValue="0") int page,
			@RequestParam(name="groupEntreprise" , defaultValue="") String entreprise,
			@RequestParam(name="groupLocalite" , defaultValue="") List<String> localites,
			@RequestParam(name="groupMotsCles" , defaultValue="") List<String> motscles
			) {
		
		if(motscles.size()==0) {
			motscles.add("%%");
		}else {
			int i=0;
			for (String str : motscles) {
				str="%"+str+"%";
				motscles.set(i, str);
				i++;
			}
		}
		
		
		if(localites.size()==0) {
			localites.add("%%");
		}else {
			int i=0;
			for (String str : localites) {
				str="%"+str+"%";
				localites.set(i, str);
				i++;
			}
		}
		Page<Annonce> annonces=annonceRepository.searchFilter(motscles, PageRequest.of(page, 9));//,localites,entreprise
		System.out.println(annonces.getNumberOfElements());
		model.addAttribute("annonces",annonces.getContent());
		model.addAttribute("pages", new int[annonces.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motcle",new String());
		model.addAttribute("localite",new String());
		return "index";
	}
	
	/*
	 * resolving form object:entrepreneurInfo  if it doesn't work get values in the default way in Get request
	 * */
	@GetMapping("/pageInscription")
	public String pageInscription(Model model) throws IOException {
		boolean isAuthenticated = false;
		boolean isEntrepreneur = false;
		Entrepreneur entrepreneur = new Entrepreneur();
		Etudiant etudiant = new Etudiant();
		
		User user = userRepository.findByUsername(httpServletRequest.getRemoteUser());
		if(user!=null) {
			isAuthenticated = true;
			List<Role> roles = user.getRoles();
			
			// get authenticated entrepreneur 
			Role entrepreneurRole = new Role();
			entrepreneurRole.setRole("ENTREPRENEUR");
			
			if(roles.contains(entrepreneurRole)) {
				isEntrepreneur = true;
				entrepreneur = entrepreneurRepository.findByEmail(user.getUsername()).get(0);
			}
			
			//get authenticated etudiant
			Role etudiantRole = new Role();
			etudiantRole.setRole("ETUDIANT");
			
			if(roles.contains(etudiantRole)) {
				isEntrepreneur = false;
				etudiant = etudiantRepository.findByEmail(user.getUsername()).get(0);
			}
		}
		model.addAttribute("nvEntrepreneur",new Entrepreneur());
		model.addAttribute("nvEtudiant",new Etudiant());
		model.addAttribute("entrepreneur",entrepreneur);
		model.addAttribute("etudiant",etudiant);
		model.addAttribute("isAuthenticated",isAuthenticated);
		model.addAttribute("isEntrepreneur",isEntrepreneur);
		return "inscription";
	}
	
	
	@PostMapping("/inscrireEntrepreneur")
	public RedirectView inscrireEntrepreneur(Model model, 
			@ModelAttribute("entrepreneur") Entrepreneur e,
			@RequestParam("profile_photo") MultipartFile photo,
			@RequestParam(name="mdp") int mdp,
			@RequestParam(name="mdpConfirmation") int mdpConfirmation,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(mdp!=mdpConfirmation) {
			model.addAttribute("errorMessage","confirmation invalide");
			return new RedirectView("/pageInscription");
		}
		if(photo.getSize()==0) {
			e.setPhotoType("image/jpg");
			e.setPhotodata(Files.readAllBytes(Paths.get("src/main/resources/static/imgs/entrepreneur2.jpg")));
		}else {
			e.setPhotoType(photo.getContentType());
			e.setPhotodata(photo.getBytes());
		}
		e.setDateCreation(new Date());
		e = entrepreneurRepository.save(e);
		e.setPhoto("PHOTO-ENTREPRENEUR-ID"+e.getId());
		e = entrepreneurRepository.save(e);
//		String fileName = fileStorageService.storeFile(file);
//		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
//				.path(fileName).toUriString();
//		e.setPhoto(fileDownloadUri);
		
		
		
		BCryptPasswordEncoder bcp=new BCryptPasswordEncoder();
		User user=new User();
		user.setUsername(e.getEmail());user.setPassword(bcp.encode(Integer.toString(mdp)));user.setActive(true);
		user.addRole(roleRepository.findByRole("ENTREPRENEUR"));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
		userRepository.save(user);
		
		return new RedirectView("/index");
	}
	
	@GetMapping("/profile/imageDisplay")
	  public void showImage(@RequestParam("id") Long profileId,
			  @RequestParam("isEntrepreneur") boolean isEntrepreneur,
			  HttpServletResponse response,HttpServletRequest request) 
	          throws ServletException, IOException{
		if(isEntrepreneur) {
			Entrepreneur entrepreneur = entrepreneurRepository.findById(profileId).get();
		    response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		    response.getOutputStream().write(entrepreneur.getPhotodata());
		    response.getOutputStream().close();
		}else {
			Etudiant etudiant = etudiantRepository.findById(profileId).get();
		    response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		    response.getOutputStream().write(etudiant.getPhotodata());
		    response.getOutputStream().close();
		}
	    
	}
	
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
	
	@GetMapping("/profileEtudiant/downloadCv")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@RequestParam(name="cvId") Long cvId) {
		Etudiant etudiant = etudiantRepository.findById(cvId).get();
		Resource file = storageService.loadAsResource(etudiant.getCvName());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + etudiant.getNom()+"_"+etudiant.getPrenom()+".pdf" + "\"").body(file);
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
	
	
	
	
	@GetMapping("/profile")
	public RedirectView redirectProfile() {
		
		User user = userRepository.findByUsername(httpServletRequest.getRemoteUser());
		if(user==null)return new RedirectView("/login");
		List<Role> roles = user.getRoles();
		Role entrepreneurRole = new Role();
		entrepreneurRole.setRole("ENTREPRENEUR");
		
		if(roles.contains(entrepreneurRole)) {
			
			return new RedirectView("/entrepreneurProfile");
			
		}else {
			return new RedirectView("/etudiantProfile");
		}
		
	}
	
	@GetMapping("/entrepreneurProfile")
	public String EntrepreneurProfile(Model model,@RequestParam(name="page" , defaultValue="0") int page) {
		boolean isAuthenticated = false;
		User user = userRepository.findByUsername(httpServletRequest.getRemoteUser());
		if(user==null) return "login";
		else {
			isAuthenticated = true;
		}
		Entrepreneur entrepreneur = entrepreneurRepository.findByEmail(httpServletRequest.getRemoteUser()).get(0);
		model.addAttribute("entrepreneur",entrepreneur);
		
		Page<Annonce> sliderAnnonces=annonceRepository.findByEntrepreneur(entrepreneur, PageRequest.of(0, 4));
		model.addAttribute("sliderAnnonces",sliderAnnonces.getContent());


		Page<Annonce> annonces=annonceRepository.findByEntrepreneur(entrepreneur, PageRequest.of(page, 9));
		model.addAttribute("annonces",annonces.getContent());
		model.addAttribute("pages", new int[annonces.getTotalPages()]);
		model.addAttribute("isAuthenticated",isAuthenticated);
		model.addAttribute("isEntrepreneur",true);
		model.addAttribute("currentPage", page);
		Annonce a = new Annonce();
		a.setTitre("Job");
		model.addAttribute("nvAnnonce", a);
		
		return "profile";
	}
	
	@PostMapping("/modifierProfileEntrepreneur")
	public RedirectView modifierProfileEntrepreneur(Model model,
			@ModelAttribute("entrepreneur") Entrepreneur e,
			@RequestParam("profile_photo") MultipartFile file,
			@RequestParam(name="nouveaumdp") String nouveaumdp) throws IOException {

		User user = userRepository.findByUsername(httpServletRequest.getRemoteUser());
		System.out.println(user.getUsername());
		System.out.println(e.getEmail());
		if(user==null) return new RedirectView("/login");
		
		Entrepreneur entrepreneur = entrepreneurRepository.findByEmail(httpServletRequest.getRemoteUser()).get(0);
		entrepreneur.setAdresse(e.getAdresse());entrepreneur.setDescription(e.getDescription());entrepreneur.setEmail(e.getEmail());entrepreneur.setNom(e.getNom());
		entrepreneur.setNomEntreprise(e.getNomEntreprise());entrepreneur.setPhoneNmbr(e.getPhoneNmbr());entrepreneur.setPrenom(e.getPrenom());entrepreneur.setPhoto(e.getPhoto());
		entrepreneur = entrepreneurRepository.save(entrepreneur);
		
		if(user.getUsername()!=e.getEmail() ) {
			user.setUsername(e.getEmail());
			userRepository.save(user);
			return new RedirectView("/login");
		}
		
		return new RedirectView("/profile");
	}
	
	@PostMapping("/photoEntrepreneur")
	public RedirectView photoEntrepreneur(Model model,
			@RequestParam("profile_photo") MultipartFile photo) throws IOException {
		User user = userRepository.findByUsername(httpServletRequest.getRemoteUser());
		if(user==null) return new RedirectView("/login");
		Entrepreneur entrepreneur = entrepreneurRepository.findByEmail(httpServletRequest.getRemoteUser()).get(0);
		
		if(photo.getSize()==0) {
			entrepreneur.setPhotoType("image/jpg");
			entrepreneur.setPhotodata(Files.readAllBytes(Paths.get("src/main/resources/static/imgs/entrepreneur2.jpg")));
		}else {
			entrepreneur.setPhotoType(photo.getContentType());
			entrepreneur.setPhotodata(photo.getBytes());
		}
		entrepreneur.setPhoto("PHOTO-ENTREPRENEUR-ID"+entrepreneur.getId());
		
		entrepreneur = entrepreneurRepository.save(entrepreneur);
		
		return new RedirectView("/profile");
	}
	
	@PostMapping("/publierAnnonce")
	public RedirectView publierAnnonce(Model model,
			@RequestParam(name="page" , defaultValue="0") int page,
			@ModelAttribute("nvAnnonce") Annonce a) {
		User user = userRepository.findByUsername(httpServletRequest.getRemoteUser());
		if(user==null) return new RedirectView("/login");
		Entrepreneur entrepreneur = entrepreneurRepository.findByEmail(httpServletRequest.getRemoteUser()).get(0);
		
		Annonce annonce = new Annonce(null, entrepreneur, null, null, a.getTitre(), a.getDescription(), new Date(), null, true, a.getLieu());
		if(a.getLieu()=="") {
			annonce.setLieu(entrepreneur.getAdresse());
		}
		
		annonceRepository.save(annonce);
		return new RedirectView("/profile");
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

























