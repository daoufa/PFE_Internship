package com.StagePFE.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@DiscriminatorValue("etudiant")
@Data @NoArgsConstructor 
public class Etudiant extends Profile {

//	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinColumn(name = "profile_id")
//	private Profile profile;

	@OneToMany(mappedBy = "etudiant",fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private List<EtudiantAnnonce> etudiantAnnonces;
	private String cv;
	
	public Etudiant(String email, String nom, String prenom, Date dateCreation, String photo, String video,
			String description, String adresse, String phoneNmbr, String cv) {
		super(email, nom, prenom, dateCreation, photo, video, description, adresse, phoneNmbr);
		this.cv=cv;
	}
	
	public void addEtudiantAnnonce(EtudiantAnnonce etdAnn) {
		if (etudiantAnnonces == null) {
			etudiantAnnonces = new ArrayList<>();
		}
		etudiantAnnonces.add(etdAnn);
		etdAnn.setEtudiant(this);

	}

	@Override
	public String toString() {
		return "Etudiant [etudiantAnnonces=" + etudiantAnnonces + ", toString()=" + super.toString();
	}
	

	
	
	
}
