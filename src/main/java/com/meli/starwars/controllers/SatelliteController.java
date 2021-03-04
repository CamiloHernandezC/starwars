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

import com.meli.starwars.dto.InterceptedCommunicationDTO;
import com.meli.starwars.dto.SatelliteWrapper;
import com.meli.starwars.entity.Satellite;
import com.meli.starwars.exceptions.SatelliteNotFoundException;
import com.meli.starwars.interfaces.MessageInterceptorInterface;
import com.meli.starwars.repositories.SatelliteRepository;

@RestController
public class SatelliteController {

	private final SatelliteRepository repository;
	private final MessageInterceptorInterface messageInterceptor;

	public SatelliteController(final SatelliteRepository repository, final MessageInterceptorInterface messageInterceptorInterface) {
		this.repository = repository;
		this.messageInterceptor = messageInterceptorInterface;
	}

	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/satellites")
	List<Satellite> all() {
		return repository.findAll();
	}
	// end::get-aggregate-root[]

	@PostMapping("/satellites")
	Satellite newSatellite(@RequestBody Satellite newSatellite) {
		return repository.save(newSatellite);
	}

	@GetMapping("/satellites/{name}")
	Satellite one(@PathVariable String name) {

		return repository.findByNameIgnoreCase(name).orElseThrow(() -> new SatelliteNotFoundException(name));
	}

	@PutMapping("/satellites/{name}")
	Satellite replaceSatellite(@RequestBody Satellite newSatellite, @PathVariable String name) {

		return repository.findByNameIgnoreCase(name).map(satellite -> {
			satellite.setDistance(newSatellite.getDistance());
			satellite.setMessage(newSatellite.getMessage());
			satellite.setX(newSatellite.getX());
			satellite.setY(newSatellite.getY());
			return repository.save(satellite);
		}).orElseGet(() -> {
			newSatellite.setName(name);
			return repository.save(newSatellite);
		});
	}

	@PostMapping("/topsecret_split/{satellite_name}")
	Satellite updateSatelliteInformation(@RequestBody Satellite newSatellite, @PathVariable String satellite_name) {

		return repository.findByNameIgnoreCase(satellite_name).map(satellite -> {
			satellite.setDistance(newSatellite.getDistance());
			satellite.setMessage(newSatellite.getMessage());
			return repository.save(satellite);
		}).orElseThrow(() -> new SatelliteNotFoundException(satellite_name));
	}
	
	@GetMapping("/topsecret_split")
	InterceptedCommunicationDTO determineInformation() {
		List<Satellite> satellitesToUse = repository.findAll();
		return messageInterceptor.interceptMessage(satellitesToUse);
	}

	@PostMapping("/topsecret")
	InterceptedCommunicationDTO interceptMessage(@RequestBody SatelliteWrapper satelliteWrapper) {
		List<Satellite> satellitesToUse = new ArrayList<>();
		for (Satellite newSatellite : satelliteWrapper.getSatellites()) {
			satellitesToUse.add(
				repository.findByNameIgnoreCase(newSatellite.getName())
				    .map(satellite -> {
				      satellite.setDistance(newSatellite.getDistance());
				      satellite.setMessage(newSatellite.getMessage());
				      return repository.save(satellite);
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
