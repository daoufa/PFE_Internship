package com.StagePFE.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Visiteur implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@OneToMany(mappedBy = "visiteur",fetch = FetchType.LAZY)
	private List<LigneRegarderPlusTardVisiteur> ligneRegarderPlusTardVisiteurs;
	
	@Override
	public String toString() {
		return "Visiteur [id=" + id + ", ligneRegarderPlusTardVisiteurs=" + ligneRegarderPlusTardVisiteurs + "]";
	}
	
	
	
	
}
