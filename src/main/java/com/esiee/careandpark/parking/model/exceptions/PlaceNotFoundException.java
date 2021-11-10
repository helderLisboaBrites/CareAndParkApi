package com.esiee.careandpark.parking.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PlaceNotFoundException extends RuntimeException{
	
	public PlaceNotFoundException(int id_place) {
		super("La place "+id_place+" n'existe pas");
	}

	public PlaceNotFoundException() {
		super("La place n'existe pas");
	}
}
