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
public class Etudiant implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Profile profile;
	
	@OneToMany(mappedBy = "etudiant")
	private List<LigneEnregistrement> ligneEnregitrement;
	
	
	@OneToMany(mappedBy = "etudiant")
	private List<LigneRegarderPlusTardEtudiant> ligneRegarderPlusTardEtudiants;
	
	public Etudiant() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Etudiant(Profile profile, List<LigneEnregistrement> ligneEnregitrement,
			List<LigneRegarderPlusTardEtudiant> ligneRegarderPlusTardEtudiants) {
		super();
		this.profile = profile;
		this.ligneEnregitrement = ligneEnregitrement;
		this.ligneRegarderPlusTardEtudiants = ligneRegarderPlusTardEtudiants;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public List<LigneEnregistrement> getLigneEnregitrement() {
		return ligneEnregitrement;
	}

	public void setLigneEnregitrement(List<LigneEnregistrement> ligneEnregitrement) {
		this.ligneEnregitrement = ligneEnregitrement;
	}

	public List<LigneRegarderPlusTardEtudiant> getLigneRegarderPlusTardEtudiants() {
		return ligneRegarderPlusTardEtudiants;
	}

	public void setLigneRegarderPlusTardEtudiants(List<LigneRegarderPlusTardEtudiant> ligneRegarderPlusTardEtudiants) {
		this.ligneRegarderPlusTardEtudiants = ligneRegarderPlusTardEtudiants;
	}

	@Override
	public String toString() {
		return "Etudiant [id=" + id + ", profile=" + profile + ", ligneEnregitrement=" + ligneEnregitrement
				+ ", ligneRegarderPlusTardEtudiants=" + ligneRegarderPlusTardEtudiants + "]";
	}
	
	
	
}
