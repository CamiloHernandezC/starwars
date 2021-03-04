package com.meli.starwars.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InterceptionAdvice {
	
	@ResponseBody
	@ExceptionHandler(InterceptionException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String InterceptionHandler(InterceptionException ex) {
		return ex.getMessage();
	}
}
