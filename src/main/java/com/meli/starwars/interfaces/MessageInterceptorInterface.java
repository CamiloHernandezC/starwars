package com.meli.starwars.interfaces;

import java.util.List;

import com.meli.starwars.dto.InterceptedCommunicationDTO;
import com.meli.starwars.dto.SatelliteDTO;

public interface MessageInterceptorInterface {

	public InterceptedCommunicationDTO interceptMessage(final List<SatelliteDTO> satellites);
}
