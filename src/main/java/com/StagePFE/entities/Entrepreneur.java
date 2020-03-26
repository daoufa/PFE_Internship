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
public class Entrepreneur implements Serializable{
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Profile profile;
	
	@OneToMany(mappedBy = "entrepreneur")
	private List<Annonce> annonces;
	
	private String nomEntreprise;

	public Entrepreneur() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Entrepreneur(Profile profile, List<Annonce> annonces, String nomEntreprise) {
		super();
		this.profile = profile;
		this.annonces = annonces;
		this.nomEntreprise = nomEntreprise;
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

	public List<Annonce> getAnnonces() {
		return annonces;
	}

	public void setAnnonces(List<Annonce> annonces) {
		this.annonces = annonces;
	}

	public String getNomEntreprise() {
		return nomEntreprise;
	}

	public void setNomEntreprise(String nomEntreprise) {
		this.nomEntreprise = nomEntreprise;
	}

	@Override
	public String toString() {
		return "Entrepreneur [id=" + id + ", profile=" + profile + ", annonces=" + annonces + ", nomEntreprise="
				+ nomEntreprise + "]";
	}
	
	
}
