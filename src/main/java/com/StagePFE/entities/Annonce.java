package com.StagePFE.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Annonce implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Entrepreneur entrepreneur;

	@OneToMany(mappedBy = "annonce")
	private List<EtudiantAnnonce> etudiantAnnonces;

	@OneToMany(mappedBy = "annonce",fetch = FetchType.EAGER)
	private List<LigneRegarderPlusTardVisiteur> ligneRegarderPlusTardVisiteurs;

	private String titre;
	private String description;
	private String dateCreation;
	private String dateExperaiton;
	private Boolean isOuvert;

	public Annonce(String titre, String description, String dateCreation, String dateExperaiton, Boolean isOuvert) {
		super();
		this.titre = titre;
		this.description = description;
		this.dateCreation = dateCreation;
		this.dateExperaiton = dateExperaiton;
		this.isOuvert = isOuvert;
	}

	
	public void addEtudiantAnnonce(EtudiantAnnonce etdAnn) {
		if (etudiantAnnonces == null) {
			etudiantAnnonces = new ArrayList<>();
		}
		etudiantAnnonces.add(etdAnn);
		etdAnn.setAnnonce(this);

	}
	
	
	public void addLigneRegarderPlusTardVisiteur(LigneRegarderPlusTardVisiteur lnregarder) {
		if (ligneRegarderPlusTardVisiteurs == null) {
			ligneRegarderPlusTardVisiteurs = new ArrayList<>();
		}
		ligneRegarderPlusTardVisiteurs.add(lnregarder);
		lnregarder.setAnnonce(this);

	}


	@Override
	public String toString() {
		return "Annonce [id=" + id + ", ligneRegarderPlusTardVisiteurs=" + ligneRegarderPlusTardVisiteurs + ", titre=" + titre
				+ ", description=" + description + ", dateCreation=" + dateCreation + ", dateExperaiton="
				+ dateExperaiton + ", isOuvert=" + isOuvert + "]";
	}
	
	

	
}
