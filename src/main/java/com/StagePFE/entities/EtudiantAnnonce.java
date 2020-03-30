package com.StagePFE.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class EtudiantAnnonce implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH })
	@JoinColumn(name = "annonce_id")
	private Annonce annonce;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH })
	@JoinColumn(name = "etudiant_id")
	private Etudiant etudiant;

	private String dateCreation;

	// regarder plus tard OU Postuler
	private String typeRelation;


	@Override
	public String toString() {
		return "EtudiantAnnonce [id=" + id + ", dateCreation="
				+ dateCreation + ", typeRelation=" + typeRelation + "]";
	}

}
