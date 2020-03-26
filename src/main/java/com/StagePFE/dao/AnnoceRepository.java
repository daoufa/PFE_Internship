package com.StagePFE.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.StagePFE.entities.Annonce;

public interface AnnoceRepository extends JpaRepository<Annonce, Long>{
	
}
