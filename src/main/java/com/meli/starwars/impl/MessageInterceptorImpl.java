package com.meli.starwars.impl;

import java.util.List;
import java.util.Vector;

import org.springframework.stereotype.Service;

import com.meli.starwars.entity.Satellite;
import com.meli.starwars.exceptions.InterceptionException;
import com.meli.starwars.interfaces.MessageInterceptorInterface;
import com.meli.starwars.utils.*;

import dto.InterceptedCommunicationDTO;

@Service
public class MessageInterceptorImpl implements MessageInterceptorInterface{

	@Override
	public InterceptedCommunicationDTO interceptMessage(final List<Satellite> satellites) {
		InterceptedCommunicationDTO result = new InterceptedCommunicationDTO();
		result.setPosition(determinePosition(satellites));
		result.setMessage(determineMessage(satellites));
		return result;
	}

	private Vector<String> determineMessage(List<Satellite> satellites) {
		int largerVector = 0; 
		for (Satellite satellite : satellites) {
			if(satellite.getMessage().size()>largerVector) {
				largerVector = satellite.getMessage().size(); 
			}
		} 
		
		Vector<String> interceptedMessage = new Vector<>();
		
		final Vector<String> message1 = satellites.get(0).getMessage();
		final Vector<String> message2 = satellites.get(1).getMessage();
		final Vector<String> message3 = satellites.get(2).getMessage();
		
		int vectorSize1 = message1.size()-1;
		int vectorSize2 = message2.size()-1;
		int vectorSize3 = message3.size()-1;
		
		while(vectorSize1>0 || vectorSize2>0 || vectorSize3>0) {
			if(!message1.get(vectorSize1).equals("")) {
				interceptedMessage.add(0, message1.get(vectorSize1));
			}
			if(!message2.get(i).equals("")) {
				interceptedMessage.add(0, message2.get(i));
				continue;
			}
			if(!message3.get(i).equals("")) {
				interceptedMessage.add(0, message3.get(i));
				continue;
			}
		}
		
		for(int i = largerVector-1; i >= 0; i--) {
			
		}
		
		return interceptedMessage;
	}

	private Position determinePosition(List<Satellite> satellites) {
		try {
			final Satellite satellite1 = satellites.get(0);
			final Satellite satellite2 = satellites.get(1);
			final Satellite satellite3 = satellites.get(2);
			
			final double distance12 = Math.sqrt(Math.pow((satellite1.getX()-satellite2.getX()),2)+ Math.pow((satellite1.getY()-satellite2.getY()),2));
			final double tetha = Math.asin((satellite2.getY()-satellite1.getY())/distance12);
			
			final double i = ((satellite3.getX() - satellite1.getX())* Math.cos(tetha)) + ((satellite3.getY()-satellite1.getY())*Math.sin(tetha));
			final double j = ((satellite3.getY() - satellite1.getY())* Math.cos(tetha)) - ((satellite3.getX()-satellite1.getX())*Math.sin(tetha));
			
			final double x = (Math.pow(satellite1.getDistance(), 2)- Math.pow(satellite2.getDistance(), 2) 
					+ Math.pow(distance12,2))/(2*distance12);
			final double y =  (Math.pow(satellite1.getDistance(), 2)- Math.pow(satellite3.getDistance(), 2) 
					+ Math.pow(i,2) + Math.pow(j,2))/(2*j)-(i*x/j);
			
			final double xOrigin = Utils.round(satellite1.getX() + (x * Math.cos(tetha))-(y*Math.sin(tetha)),4);
			final double yOrigin = Utils.round(satellite1.getY() + (x * Math.sin(tetha))+(y*Math.cos(tetha)),4);
			
			if(!Double.isFinite(xOrigin) || !Double.isFinite(yOrigin)) {
				throw new InterceptionException("The position can't be determined");
			}
			final double firstCondition = Utils.round(Math.pow(satellite1.getDistance(),2)-Math.pow(x,2)-Math.pow(y,2),2);
			final double secondCondition = Utils.round(Math.pow(satellite2.getDistance(),2)-Math.pow(x-distance12,2)-Math.pow(y,2),2);
			final double thirdCondition = Utils.round(Math.pow(satellite3.getDistance(),2)-Math.pow(x-i,2)-Math.pow(y-j,2),2);
			
			if(firstCondition + secondCondition + thirdCondition != 0) {
				throw new InterceptionException("Wrong distance or position");
			}
			
			return new Position(xOrigin, yOrigin);
		}catch (ArrayIndexOutOfBoundsException notEnoughSatellites) {
			throw new InterceptionException("You need at least 3 satellites to determine position");
		}catch (ArithmeticException arithmeticException) {
			throw new InterceptionException("The position can't be determined");
		}
	}
	

}
