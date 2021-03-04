package com.meli.starwars.interfaces;

import java.util.List;

import com.meli.starwars.entity.Satellite;

import dto.InterceptedCommunicationDTO;

public interface MessageInterceptorInterface {

	public InterceptedCommunicationDTO interceptMessage(final List<Satellite> satellites);
}
