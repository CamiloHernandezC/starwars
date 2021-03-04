package com.meli.starwars.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.meli.starwars.converters.SatelliteConverter;
import com.meli.starwars.dto.InterceptedCommunicationDTO;
import com.meli.starwars.dto.SatelliteDTO;
import com.meli.starwars.dto.SatelliteWrapper;
import com.meli.starwars.exceptions.SatelliteNotFoundException;
import com.meli.starwars.interfaces.MessageInterceptorInterface;
import com.meli.starwars.repositories.SatelliteRepository;

@RestController
public class SatelliteController {

	private final SatelliteRepository repository;
	private final MessageInterceptorInterface messageInterceptor;
	private final SatelliteConverter satelliteConverter;

	public SatelliteController(final SatelliteRepository repository,
			final MessageInterceptorInterface messageInterceptorInterface, final SatelliteConverter satelliteConverter) {
		this.repository = repository;
		this.messageInterceptor = messageInterceptorInterface;
		this.satelliteConverter = satelliteConverter;
	}

	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/satellites")
	List<SatelliteDTO> all() {
		return satelliteConverter.toDTO(repository.findAll());
	}
	// end::get-aggregate-root[]

	@PostMapping("/satellites")
	SatelliteDTO newSatellite(@RequestBody SatelliteDTO newSatellite) {
		return satelliteConverter.toDTO(repository.save(satelliteConverter.toEntity(newSatellite)));
	}

	@GetMapping("/satellites/{name}")
	SatelliteDTO one(@PathVariable String name) {

		return satelliteConverter.toDTO(repository.findByNameIgnoreCase(name).orElseThrow(() 
				-> new SatelliteNotFoundException(name)));
	}

	@PutMapping("/satellites/{name}")
	SatelliteDTO replaceSatellite(@RequestBody SatelliteDTO newSatellite, @PathVariable String name) {

		return repository.findByNameIgnoreCase(name).map(satellite -> {
			satellite.setDistance(newSatellite.getDistance());
			satellite.setMessage(newSatellite.getMessage());
			satellite.setX(newSatellite.getX());
			satellite.setY(newSatellite.getY());
			return satelliteConverter.toDTO(repository.save(satellite));
		}).orElseGet(() -> {
			newSatellite.setName(name);
			return satelliteConverter.toDTO(repository.save(satelliteConverter.toEntity(newSatellite)));
		});
	}

	@PostMapping("/topsecret_split/{satellite_name}")
	SatelliteDTO updateSatelliteInformation(@RequestBody SatelliteDTO newSatellite, @PathVariable String satellite_name) {

		return repository.findByNameIgnoreCase(satellite_name).map(satellite -> {
			satellite.setDistance(newSatellite.getDistance());
			satellite.setMessage(newSatellite.getMessage());
			return satelliteConverter.toDTO(repository.save(satellite));
		}).orElseThrow(() -> new SatelliteNotFoundException(satellite_name));
	}
	
	@GetMapping("/topsecret_split")
	InterceptedCommunicationDTO determineInformation() {
		List<SatelliteDTO> satellitesToUse = satelliteConverter.toDTO(repository.findAll());
		return messageInterceptor.interceptMessage(satellitesToUse);
	}

	@PostMapping("/topsecret")
	InterceptedCommunicationDTO interceptMessage(@RequestBody SatelliteWrapper satelliteWrapper) {
		List<SatelliteDTO> satellitesToUse = new ArrayList<>();
		for (SatelliteDTO newSatellite : satelliteWrapper.getSatellites()) {
			satellitesToUse.add(
				repository.findByNameIgnoreCase(newSatellite.getName())
				    .map(satellite -> {
				      satellite.setDistance(newSatellite.getDistance());
				      satellite.setMessage(newSatellite.getMessage());
				      return satelliteConverter.toDTO(repository.save(satellite));
				  }).orElseThrow(() -> new SatelliteNotFoundException(newSatellite.getName()))
			);
		}
		
		return messageInterceptor.interceptMessage(satellitesToUse);
	}

	@DeleteMapping("/satellites/{name}")
	void deleteSatellite(@PathVariable String name) {
		repository.deleteById(name);
	}

}
