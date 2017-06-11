package ch.hsr.smartmanager.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import ch.hsr.smartmanager.data.Configuration;

public interface ConfigurationItemRepository extends MongoRepository<Configuration, String>{
	boolean existsByName(String name);
	Configuration findByName(String name);
}
