package com.meli.starwars.converters;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.meli.starwars.dto.SatelliteDTO;
import com.meli.starwars.entity.Satellite;

@Service
public class SatelliteConverter {

	public SatelliteDTO toDTO(final Satellite satellite) {
		final ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(satellite, SatelliteDTO.class);
	}
	
	public List<SatelliteDTO> toDTO(final List<Satellite> satellites) {
		List<SatelliteDTO> resultDTO = new ArrayList<>();
		final ModelMapper modelMapper = new ModelMapper();
		for (Satellite satellite : satellites) {
			resultDTO.add(modelMapper.map(satellite, SatelliteDTO.class));
		}
		return resultDTO;
	}
	
	public Satellite toEntity(final SatelliteDTO dto) {
		final ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(dto, Satellite.class);
	}
}
