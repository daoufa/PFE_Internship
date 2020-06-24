package com.StagePFE.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("entrepreneur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entrepreneur extends Profile {

//	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinColumn(name = "profile_id")
//	private Profile profile;

	@OneToMany(mappedBy = "entrepreneur", fetch = FetchType.EAGER)
	private List<Annonce> annonces;

	private String nomEntreprise;

	public Entrepreneur(String email, String nom, String prenom, Date dateCreation, String photo, String video,
			String description, String adresse, String phoneNmbr, String nomEntreprise) {
		super(email, nom, prenom, dateCreation, photo, video, description, adresse, phoneNmbr);
		this.nomEntreprise = nomEntreprise;
	}

	@Override
	public String toString() {
		return "Entrepreneur [annonces=" + annonces + ", nomEntreprise=" + nomEntreprise + ", toString()="
				+ super.toString();
	}

	public boolean equals(Object O) {
		if (O == this)
			return true;
		if (!(O instanceof Entrepreneur))
			return false;
		Entrepreneur e = (Entrepreneur) O;
		if (O == null)
			return false;
		if (this.getEmail() == null && e.getEmail() == null)
			return true;
		return (this.getEmail().equals(e.getEmail()));
	}
}
