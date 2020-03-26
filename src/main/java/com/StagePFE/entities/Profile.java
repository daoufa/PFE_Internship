package com.StagePFE.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Profile implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String email;
	private String nom;
	private String prenom;
	private String dateCreation;
	private String photo;
	private String video;
	private String description;
	private String lieu;
	public Profile(String email, String nom, String prenom, String dateCreation, String photo, String video,
			String description, String lieu) {
		super();
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
		this.dateCreation = dateCreation;
		this.photo = photo;
		this.video = video;
		this.description = description;
		this.lieu = lieu;
	}
	public Profile() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLieu() {
		return lieu;
	}
	public void setLieu(String lieu) {
		this.lieu = lieu;
	}
	@Override
	public String toString() {
		return "Profile [id=" + id + ", email=" + email + ", nom=" + nom + ", prenom=" + prenom + ", dateCreation="
				+ dateCreation + ", photo=" + photo + ", video=" + video + ", description=" + description + ", lieu="
				+ lieu + "]";
	}

}
