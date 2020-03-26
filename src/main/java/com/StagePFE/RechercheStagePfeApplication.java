package com.StagePFE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.StagePFE.dao.EtudiantRepository;
import com.StagePFE.dao.ProfileRepository;

@SpringBootApplication
public class RechercheStagePfeApplication implements CommandLineRunner{
	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private EtudiantRepository etudiantRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(RechercheStagePfeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		System.out.println();
//		System.out.println();
//		System.out.println();
//		System.out.println(profileRepository.findById(1l));
//		System.out.println();System.out.println();
////		EntityManager em=null;
////		Profile p1=em.find(Profile.class, 1);
////		etudiantRepository.save(new Etudiant(profileRepository.getOne(1l)));
//		etudiantRepository.save(new Etudiant());
//		profileRepository.findAll().forEach(p->{
//			System.out.println(p);
//			System.out.println();
//			System.out.println();
//		});;
	}

}
