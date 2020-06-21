package com.StagePFE;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.StagePFE.Storage.StorageProperties;
import com.StagePFE.dao.AnnonceRepository;
import com.StagePFE.dao.EntrepreneurRepository;
import com.StagePFE.dao.EtudiantAnnonceRepository;
import com.StagePFE.dao.EtudiantRepository;
import com.StagePFE.dao.RoleRepository;
import com.StagePFE.dao.UserRepository;
import com.StagePFE.entities.Annonce;
import com.StagePFE.entities.Entrepreneur;
import com.StagePFE.entities.Etudiant;
import com.StagePFE.entities.EtudiantAnnonce;
import com.StagePFE.entities.Role;
import com.StagePFE.entities.User;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = { ErrorMvcAutoConfiguration.class })
@EnableConfigurationProperties(StorageProperties.class)
public class RechercheStagePfeApplication implements CommandLineRunner {
//	@Autowired
//	private ProfileRepository profileRepository;
	@Autowired
	private EtudiantRepository etudiantRepository;
	@Autowired
	private EntrepreneurRepository entrepreneurRepository;

	@Autowired
	private AnnonceRepository annonceRepository;

	@Autowired
	private EtudiantAnnonceRepository etudiantAnnonceRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(RechercheStagePfeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		SimpleDateFormat smpl = new SimpleDateFormat("yyyyy-mm-dd");
		Etudiant e1 = new Etudiant("a@outlook.fr", "daoufa", "abderrahman", new Date(), "c:/photos", "E:/videos",
				"first etudiant", "marrakech", "06535", null);

		e1.setDateCreation(smpl.parse("2017-11-15"));
		e1 = etudiantRepository.save(e1);
		Etudiant e2 = etudiantRepository.save(new Etudiant("mosameh.meryem@Gmail.com", "mosameh", "meryem", new Date(),
				"c:/photos", "c:/videos", "mosameh meryem", "Marrakech", "06000666", null));
		Etudiant e3 = etudiantRepository.save(new Etudiant("daoufa@mail.com", "daoufa", "abderrahman", new Date(),
				"c:/photos", "c:/videos",
				"daoufa abderrahman Lorem ipsum dolor sit amet consectetur adipisicing elit. Atque, inventore.Lorem ipsum dolor sit amet consectetur adipisicing elit. Atque, inventore.",
				"casa", "06111616", null));
		Etudiant e4 = etudiantRepository.save(new Etudiant("b@outlook.fr", "daoufa", "abderrahman", new Date(),
				"c:/photos", "c:/videos", "daoufa abderrahman", "Ait ourir", "0666545", null));
		Entrepreneur p1 = entrepreneurRepository.save(new Entrepreneur("abdeer@outlook.fr", "daoufa", "abderrahman",
				new Date(), "c:/photos", "E:/videos",
				"first entrepreneur daoufa abderrahman Lorem ipsum dolor sit amet consectetur adipisicing elit. Atque, inventore.Lorem ipsum dolor sit amet consectetur adipisicing elit. Atque, inventore.",
				"marrakech", "0654412", "SQLi"));
		p1.setNomEntreprise("SQLi");
		Entrepreneur p2 = entrepreneurRepository.save(new Entrepreneur("mosameh1@Gmail.com", "mosameh", "meryem",
				new Date(), "c:/photos", "c:/videos", "mosameh meryem", "Marrakech", "065234", "STG"));
		p2.setNomEntreprise("Google");
		Entrepreneur p3 = entrepreneurRepository.save(new Entrepreneur("mosameh2@Gmail.com", "mosameh", "meryem",
				new Date(), "c:/photos", "c:/videos", "mosameh meryem", "Marrakech", "75755", "OCP"));
		p3.setNomEntreprise("facebook");
		Entrepreneur p4 = entrepreneurRepository.save(new Entrepreneur("mosameh3@Gmail.com", "mosameh", "meryem",
				new Date(), "c:/photos", "c:/videos", "mosameh meryem", "Marrakech", "2454", "RIDAL"));
		p4.setNomEntreprise("SQLi");
		/*
		 * EtudiantAnnonce etAnn =new EtudiantAnnonce(); Annonce a=new
		 * Annonce("SQLi Stage", "stage preambauche", "25/6/2020", "15/5/2020", true);
		 * a.setEntrepreneur(p1); etAnn.setAnnonce(a);
		 * 
		 * etAnn=etudiantAnnonceRepository.save(etAnn); System.out.println(etAnn);
		 * e1.addEtudiantAnnonce(etAnn); etudiantRepository.save(e1);
		 */
		Annonce a = new Annonce("SQLi Stage", "sqli stage developpeur java ", new Date(), new Date(), true, "Rabat");
		Annonce a1 = new Annonce("SQLi emplois", "daoufa officiis velit accusamus omnis quasi. Incidunt?", new Date(),
				new Date(), true, "casablanca");
		Annonce a2 = new Annonce("google",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Incidunt deserunt iure ex a mollitia corrupti eius saepe? Animi quibusdam autem molestias minus placeat explicabo officiis velit accusamus omnis quasi. Incidunt?",
				new Date(), new Date(), true, "marrakech");
		Annonce a11 = new Annonce("SQLi emplois", "daoufa officiis velit accusamus omnis quasi. Incidunt?", new Date(),
				new Date(), true, "casablanca");
		Annonce a21 = new Annonce("google",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Incidunt deserunt iure ex a mollitia corrupti eius saepe? Animi quibusdam autem molestias minus placeat explicabo officiis velit accusamus omnis quasi. Incidunt?",
				new Date(), new Date(), true, "marrakech");
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
		a11.setEntrepreneur(entrep);
		a11 = annonceRepository.save(a11);
		a21.setEntrepreneur(entrep);
		a21 = annonceRepository.save(a21);
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
		etAnn.setDateCreation(new Date());
		EtudiantAnnonce etAnn1 = new EtudiantAnnonce();
		etAnn.setAnnonce(a);
		etAnn.setTypeRelation("postuler");
		EtudiantAnnonce etAnn2 = new EtudiantAnnonce();
		etAnn.setAnnonce(a);
		etAnn1.setAnnonce(a11);
		etAnn2.setAnnonce(a21);
		etAnn.setTypeRelation("postuler");
		etAnn1.setTypeRelation("postuler");
		etAnn2.setTypeRelation("postuler");
		etAnn1.setDateCreation(new Date());
		etAnn2.setDateCreation(new Date());
		e33.addEtudiantAnnonce(etAnn);
		e33.addEtudiantAnnonce(etAnn1);
		e33.addEtudiantAnnonce(etAnn2);
		etudiantRepository.save(e33);

		Optional<Etudiant> result3 = etudiantRepository.findById(3l);
		if (result.isPresent()) {
			e33 = result3.get();
			System.out.println("1	1	1	1	1	1");
			System.out.println(e33);
			System.out.println("1	1	1	1	1	1");
		}

		/*
		 * creation des roles
		 */
		BCryptPasswordEncoder bcp = new BCryptPasswordEncoder();

		Role role1 = new Role();
		role1.setRole("ADMIN");

		Role role2 = new Role();
		role2.setRole("USER");

		Role role3 = new Role();
		role3.setRole("ETUDIANT");

		Role role4 = new Role();
		role4.setRole("ENTREPRENEUR");

		roleRepository.save(role1);
		roleRepository.save(role2);
		roleRepository.save(role3);
		roleRepository.save(role4);
		User user1 = new User();
		user1.setUsername("client1");
		user1.setPassword(bcp.encode("000"));
		user1.setActive(true);
		user1.addRole(role2);
		userRepository.save(user1);

		User user2 = new User();
		user2.setUsername("admin");
		user2.setPassword(bcp.encode("123"));
		user2.setActive(true);
		user2.addRole(role1);
		user2.addRole(role2);
		userRepository.save(user2);

		User user3 = new User();
		user3.setUsername("abdeer@outlook.fr");
		user3.setPassword(bcp.encode("1"));
		user3.setActive(true);
		user3.addRole(role4);
		userRepository.save(user3);

		User user4 = new User();
		user4.setUsername("daoufa@mail.com");
		user4.setPassword(bcp.encode("2"));
		user4.setActive(true);
		user4.addRole(role3);
		userRepository.save(user4);

		User user5 = new User();
		user5.setUsername("a@outlook.fr");
		user5.setPassword(bcp.encode("2"));
		user5.setActive(true);
		user5.addRole(role3);
		userRepository.save(user5);

		User user6 = new User();
		user6.setUsername("mosameh.meryem@Gmail.com");
		user6.setPassword(bcp.encode("2"));
		user6.setActive(true);
		user6.addRole(role3);
		userRepository.save(user6);
		/*
		 * end
		 */

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
