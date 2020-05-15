package com.StagePFE.dao;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.StagePFE.entities.Annonce;

public interface AnnonceRepository extends JpaRepository<Annonce, Long>{
	public Page<Annonce> findByDescriptionAndLieuContaining(String mc,String lieu,Pageable pageable);
	@Query("select a from Annonce a where a.description like :motcle and a.lieu like :lieu")
	public Page<Annonce> searchIndexPage(@Param("motcle") String motcle,@Param("lieu") String lieu,Pageable pageable);
	
	@Query(value = "select a from Annonce a where a.description IN ('%daoufa%','%sqli%') ")
	public Page<Annonce> searchFilter(@Param("motscles") List<String> motscles, Pageable pageable);
}
 //and a.lieu in :lieus and a.entrepreneur.nomEntreprise like :entreprise 
//,@Param("lieus") List<String> lieus,@Param("entreprise") String Entreprise
//@Query(value = "select a from Annonce a where a.description IN :motscles ")
//public List<Annonce> searchFilter(@Param("motscles") List<String> motscles);


