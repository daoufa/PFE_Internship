package com.StagePFE.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StagePFE.entities.Etudiant;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
	List<Etudiant> findByEmail(String email);
}
