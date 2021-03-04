package com.meli.starwars.interfaces;

import java.util.List;

import com.meli.starwars.dto.InterceptedCommunicationDTO;
import com.meli.starwars.entity.Satellite;

public interface MessageInterceptorInterface {

	public InterceptedCommunicationDTO interceptMessage(final List<Satellite> satellites);
}
