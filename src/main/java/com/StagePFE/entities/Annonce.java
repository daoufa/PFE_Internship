package com.StagePFE.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Annonce implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Entrepreneur entrepreneur;
	
	@OneToMany(mappedBy = "annonce")
	private List<LigneEnregistrement> ligneEnregitrement;

	private String titre;
	private String description;
	private String dateCreation;
	private String dateExperaiton;
	private Boolean isOuvert;

	public Annonce() {
		super();
	}

	public Annonce(Long id, Entrepreneur entrepreneur, List<LigneEnregistrement> ligneEnregitrement, String titre,
			String description, String dateCreation, String dateExperaiton, Boolean isOuvert) {
		super();
		this.id = id;
		this.entrepreneur = entrepreneur;
		this.ligneEnregitrement = ligneEnregitrement;
		this.titre = titre;
		this.description = description;
		this.dateCreation = dateCreation;
		this.dateExperaiton = dateExperaiton;
		this.isOuvert = isOuvert;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Entrepreneur getEntrepreneur() {
		return entrepreneur;
	}

	public void setEntrepreneur(Entrepreneur entrepreneur) {
		this.entrepreneur = entrepreneur;
	}

	public List<LigneEnregistrement> getLigneEnregitrement() {
		return ligneEnregitrement;
	}

	public void setLigneEnregitrement(List<LigneEnregistrement> ligneEnregitrement) {
		this.ligneEnregitrement = ligneEnregitrement;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getDateExperaiton() {
		return dateExperaiton;
	}

	public void setDateExperaiton(String dateExperaiton) {
		this.dateExperaiton = dateExperaiton;
	}

	public Boolean getIsOuvert() {
		return isOuvert;
	}

	public void setIsOuvert(Boolean isOuvert) {
		this.isOuvert = isOuvert;
	}

	@Override
	public String toString() {
		return "Annonce [id=" + id + ", entrepreneur=" + entrepreneur + ", ligneEnregitrement=" + ligneEnregitrement
				+ ", titre=" + titre + ", description=" + description + ", dateCreation=" + dateCreation
				+ ", dateExperaiton=" + dateExperaiton + ", isOuvert=" + isOuvert + "]";
	}

}
