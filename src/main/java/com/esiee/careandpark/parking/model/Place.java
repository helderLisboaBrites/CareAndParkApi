package com.esiee.careandpark.parking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotNull;

import com.esiee.careandpark.parking.model.reference.EtatPlace;
import com.esiee.careandpark.parking.model.reference.TypePlace;

@Entity
@IdClass(PlaceId.class)
public class Place {

	@Id
	@Column(name = "parking_id")
	private int parkingId;

	@Id
	@NotNull(groups = {ValidationAjout.class})
	private Integer numero;

	@NotNull(groups = {ValidationAjout.class})
	@Enumerated(EnumType.STRING)
	private TypePlace type;

	@NotNull(groups = {ValidationEtat.class})
	@Enumerated(EnumType.STRING)
	private EtatPlace etat;

	public Place() {
	}

	public Integer getNumero() {
		return numero;
	}

	public Place setNumero(Integer numero) {
		this.numero = numero;
		return this;
	}

	public int getParkingId() {
		return parkingId;
	}

	public Place setParkingId(int parkingId) {
		this.parkingId = parkingId;
		return this;
	}

	public TypePlace getType() {
		return type;
	}

	public Place setType(TypePlace type) {
		this.type = type;
		return this;
	}

	public EtatPlace getEtat() {
		return etat;
	}

	public Place setEtat(EtatPlace etat) {
		this.etat = etat;
		return this;
	}

	public interface ValidationEtat {}
	public interface ValidationAjout {}
	
}
