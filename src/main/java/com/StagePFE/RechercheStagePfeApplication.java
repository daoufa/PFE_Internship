package com.StagePFE;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.StagePFE.dao.AnnonceRepository;
import com.StagePFE.dao.EntrepreneurRepository;
import com.StagePFE.dao.EtudiantAnnonceRepository;
import com.StagePFE.dao.EtudiantRepository;
import com.StagePFE.dao.ProfileRepository;
import com.StagePFE.entities.Annonce;
import com.StagePFE.entities.Entrepreneur;
import com.StagePFE.entities.Etudiant;
import com.StagePFE.entities.EtudiantAnnonce;
import com.StagePFE.entities.Profile;

@SpringBootApplication
public class RechercheStagePfeApplication implements CommandLineRunner {
	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private EtudiantRepository etudiantRepository;
	@Autowired
	private EntrepreneurRepository entrepreneurRepository;

	@Autowired
	private AnnonceRepository annonceRepository;

	@Autowired
	private EtudiantAnnonceRepository etudiantAnnonceRepository;

	public static void main(String[] args) {
		SpringApplication.run(RechercheStagePfeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		SimpleDateFormat smpl = new SimpleDateFormat("yyyyy-mm-dd");
		Etudiant e1 = new Etudiant("abdeer@outlook.fr", "daoufa", "abderrahman", null, "c:/photos", "E:/videos",
				"first etudiant", "marrakech");

		e1.setDateCreation(smpl.parse("2017-11-15"));
		e1 = etudiantRepository.save(e1);
		Etudiant e2 = etudiantRepository.save(new Etudiant("mosameh.meryem@Gmail.com", "mosameh", "meryem", null,
				"c:/photos", "c:/videos", "mosameh meryem", "Marrakech"));
		Etudiant e3 = etudiantRepository.save(
				new Etudiant("omar@mail.com", "omar", "boskri", null, "c:/photos", "c:/videos", "boskri omar", "casa"));
		Etudiant e4 = etudiantRepository.save(new Etudiant("abdeer@outlook.fr", "daoufa", "abderrahman", null,
				"c:/photos", "c:/videos", "daoufa abderrahman", "Ait ourir"));

		Entrepreneur p1 = entrepreneurRepository.save(new Entrepreneur("abdeer@outlook.fr", "daoufa", "abderrahman",
				null, "c:/photos", "E:/videos", "first etudiant", "marrakech"));
		p1.setNomEntreprise("SQLi");
		Entrepreneur p2 = entrepreneurRepository.save(new Entrepreneur("mosameh.meryem@Gmail.com", "mosameh", "meryem",
				null, "c:/photos", "c:/videos", "mosameh meryem", "Marrakech"));
		p2.setNomEntreprise("Google");
		Entrepreneur p3 = entrepreneurRepository.save(new Entrepreneur("mosameh.meryem@Gmail.com", "mosameh", "meryem",
				null, "c:/photos", "c:/videos", "mosameh meryem", "Marrakech"));
		p3.setNomEntreprise("facebook");
		Entrepreneur p4 = entrepreneurRepository.save(new Entrepreneur("mosameh.meryem@Gmail.com", "mosameh", "meryem",
				null, "c:/photos", "c:/videos", "mosameh meryem", "Marrakech"));
		p4.setNomEntreprise("SQLi");
		/*
		 * EtudiantAnnonce etAnn =new EtudiantAnnonce(); Annonce a=new
		 * Annonce("SQLi Stage", "stage preambauche", "25/6/2020", "15/5/2020", true);
		 * a.setEntrepreneur(p1); etAnn.setAnnonce(a);
		 * 
		 * etAnn=etudiantAnnonceRepository.save(etAnn); System.out.println(etAnn);
		 * e1.addEtudiantAnnonce(etAnn); etudiantRepository.save(e1);
		 */
		Annonce a = new Annonce("SQLi Stage", "stage preambauche", "25/6/2020", "15/5/2020", true);
		Annonce a1 = new Annonce("SQLi emplois", "sCDI", "02/02/2020", "15/5/2020", true);
		Annonce a2 = new Annonce("google", "CDI", "7/03/2020", "15/5/2020", true);
		long id = 1l;
		Entrepreneur entrep = null;
		Optional<Entrepreneur> result = entrepreneurRepository.findById(1l);
		if (result.isPresent()) {
			entrep = result.get();
			System.out.println("//////\\\\\\\\");
			System.out.println(entrep);
			System.out.println("//////\\\\\\\\");
		}
		a.setEntrepreneur(entrep);
		a = annonceRepository.save(a);
		result = entrepreneurRepository.findById(2l);
		if (result.isPresent()) {
			entrep = result.get();
			System.out.println("//////\\\\\\\\");
			System.out.println(entrep);
			System.out.println("//////\\\\\\\\");
		}
		a1.setEntrepreneur(entrep);
		a1 = annonceRepository.save(a1);
		result = entrepreneurRepository.findById(3l);
		if (result.isPresent()) {
			entrep = result.get();
			System.out.println("//////\\\\\\\\");
			System.out.println(entrep);
			System.out.println("//////\\\\\\\\");
		}
		a2.setEntrepreneur(entrep);
		a2 = annonceRepository.save(a2);
		Optional<Entrepreneur> result1 = entrepreneurRepository.findById(1l);
		if (result.isPresent()) {
			entrep = result1.get();
			System.out.println("***----****");
			System.out.println(entrep);
			System.out.println("***----****");
		}

		Etudiant e33 = null;
		Optional<Etudiant> result2 = etudiantRepository.findById(3l);
		if (result.isPresent()) {
			e33 = result2.get();
			System.out.println("333333333");
			System.out.println(e33);
			System.out.println("3333333333");
		}
		EtudiantAnnonce etAnn = new EtudiantAnnonce();
		etAnn.setAnnonce(a);
		etAnn.setTypeRelation("postuler");
		e33.addEtudiantAnnonce(etAnn);
		etudiantRepository.save(e33);

		Optional<Etudiant> result3 = etudiantRepository.findById(3l);
		if (result.isPresent()) {
			e33 = result3.get();
			System.out.println("1	1	1	1	1	1");
			System.out.println(e33);
			System.out.println("1	1	1	1	1	1");
		}

//		Annonce a=new Annonce("SQLi Stage", "stage preambauche", "25/6/2020", "15/5/2020", true);
//		a.setEntrepreneur(p1);
//		annonceRepository.save(a);

//		EtudiantAnnonce etAnn =new EtudiantAnnonce();
//		etAnn.setAnnonce(a);
//		etAnn.setTypeRelation("postuler");
//		e1.addEtudiantAnnonce(etAnn);
//		etudiantRepository.save(e1);

//		System.out.println(e1);
//		etudiantAnnonceRepository.save(etAnn);
//		Profile p2= profileRepository.save(new Profile("abdeer@outlook.fr", "daoufa", "abderrahman", "26/04/2020", "c:/photos", "c:/videos", "daoufa abderrahman", "Ait ourir"));
//		profileRepository.save(new Profile("mosameh.meryem@Gmail.com", "mosameh", "meryem", "26/04/2020", "c:/photos", "c:/videos", "mosameh meryem", "Marrakech"));
//		profileRepository.save(new Profile("omar@mail.com", "omar", "boskri", "29/04/2020", "c:/photos", "c:/videos", "boskri omar", "casa"));

//		Etudiant e=null;
//		Optional<Etudiant> result=etudiantRepository.findById(2l);
//		if(result.isPresent()) {
//			e=result.get();
//			System.out.println("//////\\\\\\\\");
//			System.out.println(e);
//			System.out.println("//////\\\\\\\\");
//		}
//		System.out.println(p1);
//		System.out.println(p1);
//		Etudiant e=new Etudiant();
//		e.setProfile(p1);
//		p1.setEtudiant(e);
//		profileRepository.save(p1);
//		System.out.println("------88888888------");
//		
//		Entrepreneur e=new Entrepreneur();
//		p1.setEntrepreneur(e);
//		e.setNomEntreprise("SQLI");
//		e.setProfile(p1);
//		profileRepository.save(p1);

//		e.setNomEntreprise("SQLI");
//		e.setProfile(p1);
//		profileRepository.save(p1);

//		Profile p2=new Profile("645465465", "daoufa", "abderrahman", "26/04/2020", "c:/photos", "c:/videos", "daoufa abderrahman", "Ait ourir");
//		p2.setId(7);

//		Entrepreneur e=new Entrepreneur();
//		Profile p2=new Profile("ùùùùùùù", "daoufa", "abderrahman", "26/04/2020", "c:/photos", "c:/videos", "daoufa abderrahman", "Ait ourir");
////		p2.setId(47);
//		e.setProfile(p2);
//		p2.setEntrepreneur(e);
//		profileRepository.save(p2);

		/*
		 * Entrepreneur e=new Entrepreneur();
		 * 
		 * e.setProfile(p1); p1.setEntrepreneur(e); profileRepository.save(p1); //
		 * entrepreneurRepository.save(e); //profileRepository.save(p1);
		 * 
		 * System.out.println(p1);
		 * 
		 */

//		entrepreneurRepository.save(e);

//		System.out.println("------88888888------");System.out.println(p1);
//		Etudiant e1=null;
//		Optional<Etudiant> rsl1=etudiantRepository.findById(3l);
//		if(rsl1.isPresent()) {
//			e1=rsl1.get();
//			System.out.println(e1);
//		}

//		e.setProfile(p1);
//		etudiantRepository.save(e);

		/*
		 * Profile p2=new Profile("klklklk", "daoufa", "abderrahman", "26/04/2020",
		 * "c:/photos", "c:/videos", "daoufa abderrahman", "Ait ourir"); Etudiant e=new
		 * Etudiant(); e.setProfile(p2); etudiantRepository.save(e);
		 */

//		Etudiant e=new Etudiant();
//		Profile p2=new Profile("4562", "daoufa", "abderrahman", "26/04/2020", "c:/photos", "c:/videos", "daoufa abderrahman", "Ait ourir");
////		p2.setId(47);
//		e.setProfile(p2);
//		p2.setEtudiant(e);
//		profileRepository.save(p2);// ou etudiantRepository.save(e);

		etudiantRepository.findAll().forEach(e -> {
			System.out.println(e);
			System.out.println("*****");
		});
		;
	}

}
