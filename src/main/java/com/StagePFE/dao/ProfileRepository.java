package com.StagePFE.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.StagePFE.entities.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long>{
	
}
