package com.esiee.careandpark.parking.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseStatus(HttpStatus.CONFLICT)
public class ParkingVideException extends RuntimeException{

	public ParkingVideException(int id_parking) {
		super("Le parking "+id_parking+" est déjà vide");
	}
	
	public ParkingVideException() {
		super("Le parking est déjà vide");
	}
	

}
