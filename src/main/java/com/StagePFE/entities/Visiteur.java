package com.StagePFE.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Visiteur implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@OneToMany(mappedBy = "visiteur")
	private List<LigneRegarderPlusTardVisiteur> ligneRegarderPlusTardVisiteurs;
	
	public Visiteur() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Visiteur(List<LigneRegarderPlusTardVisiteur> ligneRegarderPlusTardVisiteurs) {
		super();
		this.ligneRegarderPlusTardVisiteurs = ligneRegarderPlusTardVisiteurs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<LigneRegarderPlusTardVisiteur> getLigneRegarderPlusTardVisiteurs() {
		return ligneRegarderPlusTardVisiteurs;
	}

	public void setLigneRegarderPlusTardVisiteurs(List<LigneRegarderPlusTardVisiteur> ligneRegarderPlusTardVisiteurs) {
		this.ligneRegarderPlusTardVisiteurs = ligneRegarderPlusTardVisiteurs;
	}

	@Override
	public String toString() {
		return "Visiteur [id=" + id + ", ligneRegarderPlusTardVisiteurs=" + ligneRegarderPlusTardVisiteurs + "]";
	}
	
	
	
	
}
