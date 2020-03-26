package com.StagePFE.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StagePFE.entities.Etudiant;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

}
