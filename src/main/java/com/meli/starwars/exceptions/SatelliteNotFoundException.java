package com.meli.starwars.exceptions;

public class SatelliteNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SatelliteNotFoundException(String name) {
	    super("Could not find satellite " + name);
	}
}
