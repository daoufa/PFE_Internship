package com.StagePFE.entities;

import java.io.Serializable;
import java.util.List;

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
public class LigneRegarderPlusTardVisiteur implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="annonce_id")
	private Annonce annonce;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="visiteur_id")
	private Visiteur visiteur;

	private String DateDAjout;

	@Override
	public String toString() {
		return "LigneRegarderPlusTardVisiteur [id=" + id + ", annonce=" + annonce + ", visiteur=" + visiteur
				+ ", DateDAjout=" + DateDAjout + "]";
	}
	
	
}
