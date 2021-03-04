package com.meli.starwars.dto;

import java.io.Serializable;
import java.util.Vector;

import com.meli.starwars.utils.Position;

/**
 * 
 * @author camilo.hernandez
 *
 */
public class InterceptedCommunicationDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Position position;
	private Vector<String> message;

	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public Vector<String> getMessage() {
		return message;
	}
	public void setMessage(Vector<String> message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "InterceptedCommunicationDTO [position=" + position + ", message=" + message + "]";
	}
	
	
}
