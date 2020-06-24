package com.StagePFE.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.StagePFE.Storage.StorageService;
import com.StagePFE.dao.AnnonceRepository;
import com.StagePFE.dao.EntrepreneurRepository;
import com.StagePFE.dao.EtudiantRepository;
import com.StagePFE.dao.UserRepository;
import com.StagePFE.entities.Annonce;
import com.StagePFE.entities.Entrepreneur;
import com.StagePFE.entities.Etudiant;
import com.StagePFE.entities.EtudiantAnnonce;
import com.StagePFE.entities.Role;
import com.StagePFE.entities.User;
import com.StagePFE.service.HomeService;

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
	private HttpServletRequest httpServletRequest;
	@Autowired
	private StorageService storageService;
	@Autowired
	private HomeService homeService;

	@GetMapping(value = "/login")
	public String homePage(Model model) {
		model.addAttribute("isAuthenticated", false);
		model.addAttribute("isEntrepreneur", false);
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
	public String index(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "motcle", defaultValue = "") String motcle,
			@RequestParam(name = "localite", defaultValue = "") String lieu) {
		boolean isAuthenticated = false;
		List<Long> listannoncesId = new ArrayList<Long>();
		boolean isEntrepreneur = false;
		Etudiant etudiant = new Etudiant();
		Entrepreneur entrepreneur = new Entrepreneur();
		User user = userRepository.findByUsername(httpServletRequest.getRemoteUser());
		if (user != null) {
			isAuthenticated = true;
			List<Role> roles = user.getRoles();

			// get authenticated entrepreneur
			Role entrepreneurRole = new Role();
			entrepreneurRole.setRole("ENTREPRENEUR");

			if (roles.contains(entrepreneurRole)) {
				isEntrepreneur = true;
				entrepreneur = entrepreneurRepository.findByEmail(user.getUsername()).get(0);
			}

			Role etudiantRole = new Role();
			etudiantRole.setRole("ETUDIANT");

			if (roles.contains(etudiantRole)) {
				isEntrepreneur = false;
				etudiant = etudiantRepository.findByEmail(user.getUsername()).get(0);
				for (EtudiantAnnonce etudiantAnnonce : etudiant.getEtudiantAnnonces()) {
					listannoncesId.add(etudiantAnnonce.getAnnonce().getId());
				}
			}

		}

		Page<Annonce> annonces = annonceRepository.searchIndexPage("%" + motcle + "%", "%" + lieu + "%",
				PageRequest.of(page, 9));
		model.addAttribute("annonces", annonces.getContent());
		model.addAttribute("pages", new int[annonces.getTotalPages()]);
		model.addAttribute("listannoncesId", listannoncesId);
		model.addAttribute("isAuthenticated", isAuthenticated);
		model.addAttribute("isEntrepreneur", isEntrepreneur);
		model.addAttribute("etudiant", etudiant);
		model.addAttribute("entrepreneur", entrepreneur);
		model.addAttribute("currentPage", page);
		model.addAttribute("motcle", motcle);
		model.addAttribute("localite", lieu);
		return "index";
	}

	@GetMapping("/index/filter")
	public String index(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "groupEntreprise", defaultValue = "") String entreprise,
			@RequestParam(name = "groupLocalite", defaultValue = "") List<String> localites,
			@RequestParam(name = "groupMotsCles", defaultValue = "") List<String> motscles) {

		Page<Annonce> annonces = homeService.trier(entreprise, localites, motscles, page);
		model.addAttribute("annonces", annonces.getContent());
		model.addAttribute("pages", new int[annonces.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motcle", new String());
		model.addAttribute("localite", new String());
		return "index";
	}

	@GetMapping("/pageInscription")
	public String pageInscription(Model model) throws IOException {
		boolean isAuthenticated = false;
		boolean isEntrepreneur = false;
		Entrepreneur entrepreneur = new Entrepreneur();
		Etudiant etudiant = new Etudiant();

		User user = userRepository.findByUsername(httpServletRequest.getRemoteUser());
		if (user != null) {
			isAuthenticated = true;
			List<Role> roles = user.getRoles();

			// get authenticated entrepreneur
			Role entrepreneurRole = new Role();
			entrepreneurRole.setRole("ENTREPRENEUR");

			if (roles.contains(entrepreneurRole)) {
				isEntrepreneur = true;
				entrepreneur = entrepreneurRepository.findByEmail(user.getUsername()).get(0);
			}

			// get authenticated etudiant
			Role etudiantRole = new Role();
			etudiantRole.setRole("ETUDIANT");

			if (roles.contains(etudiantRole)) {
				isEntrepreneur = false;
				etudiant = etudiantRepository.findByEmail(user.getUsername()).get(0);
			}
		}
		model.addAttribute("nvEntrepreneur", new Entrepreneur());
		model.addAttribute("nvEtudiant", new Etudiant());
		model.addAttribute("entrepreneur", entrepreneur);
		model.addAttribute("etudiant", etudiant);
		model.addAttribute("isAuthenticated", isAuthenticated);
		model.addAttribute("isEntrepreneur", isEntrepreneur);

		return "inscription";
	}

	@GetMapping("/profile/imageDisplay")
	public void showImage(@RequestParam("id") Long profileId, @RequestParam("isEntrepreneur") boolean isEntrepreneur,
			HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
		if (isEntrepreneur) {
			Entrepreneur entrepreneur = entrepreneurRepository.findById(profileId).get();
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
			response.getOutputStream().write(entrepreneur.getPhotodata());
			response.getOutputStream().close();
		} else {
			Etudiant etudiant = etudiantRepository.findById(profileId).get();
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
			response.getOutputStream().write(etudiant.getPhotodata());
			response.getOutputStream().close();
		}

	}

	@GetMapping("/profileEtudiant/downloadCv")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@RequestParam(name = "cvId") Long cvId) {
		Etudiant etudiant = etudiantRepository.findById(cvId).get();
		Resource file = storageService.loadAsResource(etudiant.getCvName());
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + etudiant.getNom() + "_" + etudiant.getPrenom() + ".pdf" + "\"")
				.body(file);
	}

	@GetMapping("/profile")
	public RedirectView redirectProfile() {

		User user = userRepository.findByUsername(httpServletRequest.getRemoteUser());
		if (user == null)
			return new RedirectView("/login");
		List<Role> roles = user.getRoles();
		Role entrepreneurRole = new Role();
		entrepreneurRole.setRole("ENTREPRENEUR");

		if (roles.contains(entrepreneurRole)) {

			return new RedirectView("/entrepreneurProfile");

		} else {
			return new RedirectView("/etudiantProfile");
		}

	}

}
