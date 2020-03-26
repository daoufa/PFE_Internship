package com.StagePFE.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class LigneRegarderPlusTardEtudiant implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Annonce annonce;

	@ManyToOne
	private Etudiant etudiant;

	private String DateDAjout;

	public LigneRegarderPlusTardEtudiant() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LigneRegarderPlusTardEtudiant(Annonce annonce, Etudiant etudiant, String dateDAjout) {
		super();
		this.annonce = annonce;
		this.etudiant = etudiant;
		DateDAjout = dateDAjout;
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

	public String getDateDAjout() {
		return DateDAjout;
	}

	public void setDateDAjout(String dateDAjout) {
		DateDAjout = dateDAjout;
	}

	@Override
	public String toString() {
		return "LigneRegarderPlusTardEtudiant [id=" + id + ", annonce=" + annonce + ", etudiant=" + etudiant
				+ ", DateDAjout=" + DateDAjout + "]";
	}

	
}
