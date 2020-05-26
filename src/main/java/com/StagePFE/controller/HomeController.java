package com.StagePFE.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.StagePFE.dao.AnnonceRepository;
import com.StagePFE.dao.EntrepreneurRepository;
import com.StagePFE.dao.EtudiantRepository;
import com.StagePFE.dao.ProfileRepository;
import com.StagePFE.dao.RoleRepository;
import com.StagePFE.dao.UserRepository;
import com.StagePFE.entities.Annonce;
import com.StagePFE.entities.Entrepreneur;
import com.StagePFE.entities.Etudiant;
import com.StagePFE.entities.Profile;
import com.StagePFE.entities.Role;
import com.StagePFE.entities.User;

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
//	@Autowired
//	private ProfileRepository profileRepository;
	
	
	@GetMapping("/login")
	public String login(Model model) {
		return "test";
	}
	
	
	@GetMapping("/")
	public RedirectView redirectIndex() {
		return new RedirectView("/index");
	}
	/*
	 * @AuthenticationPrincipal User user,
	 * */
	@GetMapping("/index")
	public String index(Model model,
			@RequestParam(name="page" , defaultValue="0") int page,
			@RequestParam(name="motcle" , defaultValue="") String motcle,
			@RequestParam(name="localite" , defaultValue="") String lieu
			) {
		Page<Annonce> annonces=annonceRepository.searchIndexPage("%"+motcle+"%","%"+lieu+"%", PageRequest.of(page, 9));
		model.addAttribute("annonces",annonces.getContent());
		model.addAttribute("pages", new int[annonces.getTotalPages()]);
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
		System.out.println("localite");
		System.out.println(localites.toString());
		
		System.out.println("motscles");
		System.out.println(motscles.toString());
		
		System.out.println("entreprise");
		entreprise = "%"+entreprise+"%";
		System.out.println(entreprise);
		/*
		 * List<Annonce> annonces=annonceRepository.searchFilter(motscles);
		model.addAttribute("pages", new int[1]);
		model.addAttribute("annonces",annonces);
		 * */
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
	public String pageInscription(Model model) {
		model.addAttribute("entrepreneur",new Entrepreneur());
		model.addAttribute("etudiant",new Etudiant());
		return "inscription";
	}
	
	
	@PostMapping("/inscrireEntrepreneur")
	public String inscrireEntrepreneur(Model model, @ModelAttribute("entrepreneur") Entrepreneur e,
			@RequestParam(name="mdp") int mdp,
			@RequestParam(name="mdpConfirmation") int mdpConfirmation,
			@RequestParam(name="page" , defaultValue="0") int page,
			@RequestParam(name="motcle" , defaultValue="") String motcle,
			@RequestParam(name="localite" , defaultValue="") String lieu) {
		if(mdp!=mdpConfirmation) {
			model.addAttribute("errorMessage","confirmation invalide");
			return "inscription";
		}
		e.setDateCreation(new Date());
		entrepreneurRepository.save(e);
		
		BCryptPasswordEncoder bcp=new BCryptPasswordEncoder();
		User user=new User();
		user.setUsername(e.getEmail());user.setPassword(bcp.encode(Integer.toString(mdp)));user.setActive(true);
		user.addRole(roleRepository.findByRole("ENTREPRENEUR"));
		userRepository.save(user);
		
//		redirection vers index
		Page<Annonce> annonces=annonceRepository.searchIndexPage("%"+motcle+"%","%"+lieu+"%", PageRequest.of(page, 9));
		model.addAttribute("annonces",annonces.getContent());
		model.addAttribute("pages", new int[annonces.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motcle",motcle);
		model.addAttribute("localite",lieu);
		return "index";
	}
	
	
	
	@PostMapping("/inscrireEtudiant")
	public String inscrireEtudiant(Model model, @ModelAttribute("etudiant") Etudiant e,
			@RequestParam(name="mdp") int mdp,
			@RequestParam(name="mdpConfirmation") int mdpConfirmation,
			@RequestParam(name="page" , defaultValue="0") int page,
			@RequestParam(name="motcle" , defaultValue="") String motcle,
			@RequestParam(name="localite" , defaultValue="") String lieu) {
		if(mdp!=mdpConfirmation) {
			model.addAttribute("errorMessage","confirmation invalide");
			return "inscription";
		}
		e.setDateCreation(new Date());
		etudiantRepository.save(e);
		
		BCryptPasswordEncoder bcp=new BCryptPasswordEncoder();
		User user=new User();
		user.setUsername(e.getEmail());user.setPassword(bcp.encode(Integer.toString(mdp)));user.setActive(true);
		user.addRole(roleRepository.findByRole("ETUDIANT"));
		userRepository.save(user);
		
//		redirection vers index
		Page<Annonce> annonces=annonceRepository.searchIndexPage("%"+motcle+"%","%"+lieu+"%", PageRequest.of(page, 9));
		model.addAttribute("annonces",annonces.getContent());
		model.addAttribute("pages", new int[annonces.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motcle",motcle);
		model.addAttribute("localite",lieu);
		return "index";
	}
	
	@GetMapping("/profile")
	public RedirectView redirectProfile() {
		
		User user = userRepository.findByUsername(httpServletRequest.getRemoteUser());
		if(user==null)new RedirectView("/login");
		
		List<Role> roles = user.getRoles();
		Role entrepreneurRole = new Role();
		entrepreneurRole.setRole("ENTREPRENEUR");
		
		if(roles.contains(entrepreneurRole)) {
			
			return new RedirectView("/EntrepreneurProfile");
			
		}else {
			return new RedirectView("/EtudiantProfile");
		}
		
	}
	
	@GetMapping("/EntrepreneurProfile")
	public String EntrepreneurProfile(Model model) {
		User user = userRepository.findByUsername(httpServletRequest.getRemoteUser());
		if(user==null) return "login";
		System.out.println(httpServletRequest.getRemoteUser());
		Entrepreneur entrepreneur = entrepreneurRepository.findByEmail(httpServletRequest.getRemoteUser()).get(0);
		model.addAttribute("entrepreneur",entrepreneur);
		return "profile";
	}
}

























