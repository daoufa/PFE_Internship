package com.StagePFE.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.StagePFE.entities.Entrepreneur;
import java.lang.String;
import java.util.List;

public interface EntrepreneurRepository extends JpaRepository<Entrepreneur, Long> {
	
//	@Query("select e from Entrepreneur e where e.email = :email")
//	Entrepreneur findByEmail(@Param("email") String email);
	
	List<Entrepreneur> findByEmail(String email);
}
