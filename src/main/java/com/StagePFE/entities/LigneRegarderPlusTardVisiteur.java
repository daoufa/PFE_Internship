package com.StagePFE.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class LigneRegarderPlusTardVisiteur implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Annonce annonce;

	@ManyToOne
	private Visiteur visiteur;

	private String DateDAjout;

	public LigneRegarderPlusTardVisiteur() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LigneRegarderPlusTardVisiteur(Annonce annonce, Visiteur visiteur, String dateDAjout) {
		super();
		this.annonce = annonce;
		this.visiteur = visiteur;
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

	public Visiteur getVisiteur() {
		return visiteur;
	}

	public void setEtudiant(Visiteur visiteur) {
		this.visiteur = visiteur;
	}

	public String getDateDAjout() {
		return DateDAjout;
	}

	public void setDateDAjout(String dateDAjout) {
		DateDAjout = dateDAjout;
	}

	@Override
	public String toString() {
		return "LigneRegarderPlusTardVisiteur [id=" + id + ", annonce=" + annonce + ", visiteur=" + visiteur
				+ ", DateDAjout=" + DateDAjout + "]";
	}
	
	
}
