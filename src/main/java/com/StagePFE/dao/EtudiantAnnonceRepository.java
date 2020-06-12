package com.StagePFE.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.StagePFE.entities.Etudiant;
import com.StagePFE.entities.EtudiantAnnonce;

public interface EtudiantAnnonceRepository extends JpaRepository<EtudiantAnnonce, Long>{
	public Page<EtudiantAnnonce> findByEtudiant(Etudiant etudiant,Pageable page);
}
