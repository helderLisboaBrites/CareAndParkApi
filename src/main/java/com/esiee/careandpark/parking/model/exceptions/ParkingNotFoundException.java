package com.esiee.careandpark.parking.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ParkingNotFoundException extends RuntimeException{

	public ParkingNotFoundException(int id_parking) {
		super("Le parking "+id_parking+" n'existe pas");
	}
	
	public ParkingNotFoundException() {
		super("Le parking n'existe pas");
	}
	

}
