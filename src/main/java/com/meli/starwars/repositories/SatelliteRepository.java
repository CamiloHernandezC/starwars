package com.meli.starwars.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meli.starwars.entity.Satellite;

public interface SatelliteRepository extends JpaRepository<Satellite, String>{
    Optional<Satellite> findByNameIgnoreCase(String name);

}
