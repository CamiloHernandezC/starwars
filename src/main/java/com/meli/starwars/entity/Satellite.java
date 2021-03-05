package com.meli.starwars.entity;

import java.util.Objects;
import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Satellite {

	private @Id String name; 
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


	public Satellite() {}

	public Satellite(String name, double x, double y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	public Satellite(String name, double x, double y, double distance, Vector<String> message) {
		super();
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
	    if (!(o instanceof Satellite))
	      return false;
	    Satellite satellite = (Satellite) o;
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
