package com.meli.starwars.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.meli.starwars.entity.Satellite;
import com.meli.starwars.repositories.SatelliteRepository;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(SatelliteRepository repository) {
		return args -> {
			log.info("Preloading " + repository.save(new Satellite(0, 2*100*Math.sqrt(3), "Kenobi")));
			log.info("Preloading " + repository.save(new Satellite(100, 0, "Skywalker")));
			log.info("Preloading " + repository.save(new Satellite(-100, 0, "Sato")));
	    };
	}
}
