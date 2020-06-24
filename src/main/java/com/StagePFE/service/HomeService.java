package com.StagePFE.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.StagePFE.dao.AnnonceRepository;
import com.StagePFE.entities.Annonce;

@Service
public class HomeService {
	@Autowired
	private AnnonceRepository annonceRepository;

	public Page<Annonce> trier(String entreprise, List<String> localites, List<String> motscles, int page) {
//		if (motscles.size() == 0) {
//			motscles.add("%%");
//		} else {
//			int i = 0;
//			for (String str : motscles) {
//				str = "%" + str + "%";
//				motscles.set(i, str);
//				i++;
//			}
//		}
//
//		if (localites.size() == 0 || localites.equals("Maroc")) {
//			localites.add("%%");
//		}
//
//		if (entreprise.equals("")) {
//			entreprise = "%%";
//		}
//
//		System.out.println("--------motsCles---------");
//		for (String s : motscles) {
//			System.out.println(s);
//		}
//		System.out.println("--------localites---------");
//		for (String s : localites) {
//			System.out.println(s);
//		}
//		System.out.println("-------entreprise--------");
//		System.out.println(entreprise);

		List<String> lieu = new ArrayList<String>();
		lieu.add("%rabat%");
		lieu.add("%casa%");
		Page<Annonce> annonces = null;
		annonces = annonceRepository.searchFilter(PageRequest.of(0, 9));// ,localites,entreprise
		System.out.println(annonces.getNumberOfElements());
		return annonces;
	}
}
