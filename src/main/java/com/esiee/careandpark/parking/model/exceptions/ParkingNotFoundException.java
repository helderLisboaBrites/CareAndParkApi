package com.esiee.careandpark.parking.model.exceptions;

public class ParkingNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ParkingNotFoundException(String name) {
		super("la parking "+name+" n'existe pas");
	}
	
	

}
