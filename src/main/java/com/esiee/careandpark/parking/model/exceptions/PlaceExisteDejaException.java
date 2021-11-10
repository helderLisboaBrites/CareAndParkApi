package com.esiee.careandpark.parking.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class PlaceExisteDejaException extends RuntimeException{
	
	public PlaceExisteDejaException(int id_place, int id_parking) {
		super("La place numero "+id_place+" parking "+id_parking+" existe déjà");
	}

	public PlaceExisteDejaException() {
		super("La place existe déjà");
	}
}
