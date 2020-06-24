package com.StagePFE.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("etudiant")
@Data
@NoArgsConstructor
public class Etudiant extends Profile {

//	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinColumn(name = "profile_id")
//	private Profile profile;

	@OneToMany(mappedBy = "etudiant", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private List<EtudiantAnnonce> etudiantAnnonces;
	private String cvName;

	public Etudiant(String email, String nom, String prenom, Date dateCreation, String photo, String video,
			String description, String adresse, String phoneNmbr, String cv) {
		super(email, nom, prenom, dateCreation, photo, video, description, adresse, phoneNmbr);
		this.cvName = cv;
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

	public boolean equals(Object O) {
		if (O == this)
			return true;
		if (!(O instanceof Etudiant))
			return false;
		Etudiant e = (Etudiant) O;
		if (O == null)
			return false;
		if (this.getEmail() == null && e.getEmail() == null)
			return true;
		return (this.getEmail().equals(e.getEmail()));
	}

}
