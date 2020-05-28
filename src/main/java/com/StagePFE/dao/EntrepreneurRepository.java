package com.StagePFE.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.StagePFE.entities.Annonce;
import com.StagePFE.entities.Entrepreneur;
import java.lang.String;
import java.util.List;

public interface EntrepreneurRepository extends JpaRepository<Entrepreneur, Long> {
//	@Query("select e.annonces from Entrepreneur e where a.description like :motcle and a.lieu like :lieu")
//	public Page<Annonce> findAllAnnonces(Pageable pageable);
	
	List<Entrepreneur> findByEmail(String email);
}
