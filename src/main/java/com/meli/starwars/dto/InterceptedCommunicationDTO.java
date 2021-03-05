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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InterceptedCommunicationDTO other = (InterceptedCommunicationDTO) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}
	
	
}
