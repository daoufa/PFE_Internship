package com.StagePFE.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.StagePFE.entities.Profile;
import java.lang.String;
import java.util.List;
@NoRepositoryBean
public interface ProfileRepository extends JpaRepository<Profile, Long>{
	List<Profile> findByEmail(String email);
}
