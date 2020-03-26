package com.StagePFE.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.StagePFE.entities.Visiteur;

public interface VisiteurRepository extends JpaRepository<Visiteur, Long>{
	
}
