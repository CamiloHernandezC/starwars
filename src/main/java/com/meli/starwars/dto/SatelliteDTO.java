package com.meli.starwars.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.Vector;

public class SatelliteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name; 
	private double x,y;//Cartesian coordinates
	private double distance;
	private Vector<String> message;
	
	
	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Vector<String> getMessage() {
		return message;
	}

	public void setMessage(Vector<String> message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}


	public SatelliteDTO() {}

	public SatelliteDTO(String name, double x, double y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	 public SatelliteDTO(String name, double x, double y, double distance, Vector<String> message) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.distance = distance;
		this.message = message;
	}

	@Override
	  public boolean equals(Object o) {

	    if (this == o)
	      return true;
	    if (!(o instanceof SatelliteDTO))
	      return false;
	    SatelliteDTO satellite = (SatelliteDTO) o;
	    return Objects.equals(this.name, satellite.name) && Objects.equals(this.x, satellite.x)
	        && Objects.equals(this.y, satellite.y);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(this.x, this.y, this.name);
	  }

	  @Override
	  public String toString() {
	    return "Satellite {" + "name='" + this.name + '\'' + ", x=" + this.x + ", y=" + this.y +
	    		 ", distance=" + this.distance + ", message='" + this.message + '\'' + '}';
	  }
	
	
}
