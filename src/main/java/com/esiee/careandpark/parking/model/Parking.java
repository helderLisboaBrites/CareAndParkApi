package com.esiee.careandpark.parking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
public class Parking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull(groups = {ValidationAjout.class})
	@NotBlank(groups = {ValidationAjout.class})
	private String nom;

	@NotNull(groups = {ValidationAjout.class})
	private String adresse;

	@JsonIgnore
	@PositiveOrZero
	private int compteur;

	public int incrementerCompteur() {
		return compteur ++;
	}

	public int decrementerCompteur() {
		if(compteur == 0) {
			throw new IllegalStateException("le compteur doit être positif");
		}
		return compteur --;
	}

	public int getCompteur() {
		return compteur;
	}
	public void setCompteur(int compteur) {
		if(compteur < 0) {
			throw new IllegalArgumentException("Le compteur ne peut pas être négatif");
		}
		this.compteur = compteur;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}


	public Parking() {
	}
	
	
	public Parking(int id, String nom, String adresse, int compteur) {
		this.id = id;
		this.nom = nom;
		this.adresse = adresse;
		this.compteur = compteur;
	}
	
	public interface ValidationAjout {}
	
	}
	