package com.StagePFE.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String email;
	private String nom;
	private String prenom;
	@Temporal(TemporalType.DATE)
	private Date dateCreation;
	private String photo;
	private String photoType;
	@Lob
	private byte[] photodata;
	private String video;
	private String description;
	private String adresse;
	private String phoneNmbr;

	public Profile(String email, String nom, String prenom, Date dateCreation, String photo, String video,
			String description, String adresse, String phoneNmbr) {
		super();
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
		this.dateCreation = dateCreation;
		this.photo = photo;
		this.video = video;
		this.description = description;
		this.adresse = adresse;
		this.phoneNmbr = phoneNmbr;
	}

	@Override
	public String toString() {
		return "Profile [id=" + id + ", email=" + email + ", nom=" + nom + ", prenom=" + prenom + ", dateCreation="
				+ dateCreation + ", photo=" + photo + ", video=" + video + ", description=" + description + ", adresse="
				+ adresse + "]";
	}

}