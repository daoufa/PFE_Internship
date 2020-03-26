package com.StagePFE.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class LigneEnregistrement implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Annonce annonce;
	
	@ManyToOne
	private Etudiant etudiant;
	
	private String DateDePostulation;

	public LigneEnregistrement() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LigneEnregistrement(Long id, Annonce annonce, Etudiant etudiant, String dateDePostulation) {
		super();
		this.id = id;
		this.annonce = annonce;
		this.etudiant = etudiant;
		DateDePostulation = dateDePostulation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Annonce getAnnonce() {
		return annonce;
	}

	public void setAnnonce(Annonce annonce) {
		this.annonce = annonce;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public String getDateDePostulation() {
		return DateDePostulation;
	}

	public void setDateDePostulation(String dateDePostulation) {
		DateDePostulation = dateDePostulation;
	}

	@Override
	public String toString() {
		return "LigneEnregistrement [id=" + id + ", annonce=" + annonce + ", etudiant=" + etudiant
				+ ", DateDePostulation=" + DateDePostulation + "]";
	}
	
	
}
