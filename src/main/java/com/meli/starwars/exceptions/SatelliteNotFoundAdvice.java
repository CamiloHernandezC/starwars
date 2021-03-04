package com.meli.starwars.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SatelliteNotFoundAdvice {
	
	@ResponseBody
	@ExceptionHandler(SatelliteNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String satelliteNotFoundHandler(SatelliteNotFoundException ex) {
		return ex.getMessage();
	}
}
