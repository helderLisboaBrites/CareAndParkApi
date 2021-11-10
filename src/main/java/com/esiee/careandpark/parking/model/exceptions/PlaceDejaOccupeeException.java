package com.esiee.careandpark.parking.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseStatus(HttpStatus.CONFLICT)
public class PlaceDejaOccupeeException extends RuntimeException{
	
	public PlaceDejaOccupeeException(int id_place) {
		super("La place "+id_place+" est déjà occupée");
	}

	public PlaceDejaOccupeeException() {
		super("La place est déjà occupée");
	}
}
