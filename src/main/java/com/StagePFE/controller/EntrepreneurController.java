package com.StagePFE.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

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
import org.springframework.web.servlet.view.RedirectView;

import com.StagePFE.dao.AnnonceRepository;
import com.StagePFE.dao.EntrepreneurRepository;
import com.StagePFE.dao.RoleRepository;
import com.StagePFE.dao.UserRepository;
import com.StagePFE.entities.Annonce;
import com.StagePFE.entities.Entrepreneur;
import com.StagePFE.entities.User;


@Controller
public class EntrepreneurController {
	@Autowired
	private AnnonceRepository annonceRepository;
	@Autowired
	private EntrepreneurRepository entrepreneurRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private HttpServletRequest httpServletRequest;
	
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
}
