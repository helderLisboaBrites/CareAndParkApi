package com.esiee.careandpark.parking.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.esiee.careandpark.parking.model.reference.EtatPlace;
import com.esiee.careandpark.parking.model.reference.TypePlace;

@Entity
public class Place {
	@Id
	@GeneratedValue
	private int id;

	@Enumerated(EnumType.STRING)
	private final TypePlace type;

	
	private EtatPlace etat;
	private int numero;
	
	public Place(TypePlace type,int numero) {
		this.type=type;
		this.etat=EtatPlace.Libre;
		this.numero = numero;
		
	}
	
	public TypePlace getType() {
		return type;
	}
	
	public EtatPlace getEtat() {
		return etat;
	}
	
	public void setEtat(EtatPlace etat) {
		this.etat=etat;
	}
	
	public int getNumero() {
		return this.numero;
	}

}
