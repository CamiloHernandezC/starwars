package com.meli.starwars.exceptions;

public class InterceptionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InterceptionException(String exceptionInfo) {
	    super("We couldn't intercept the message. " + exceptionInfo);
	}
}
