package com.StagePFE.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.StagePFE.dao.AnnonceRepository;
import com.StagePFE.entities.Annonce;
@Controller
public class HomeController {
	@Autowired
	private AnnonceRepository annonceRepository;
	@GetMapping("/")
	public RedirectView redirect() {
		return new RedirectView("/index");
	}
	
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
	
}
