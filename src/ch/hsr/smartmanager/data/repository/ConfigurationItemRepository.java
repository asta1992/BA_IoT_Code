package ch.hsr.smartmanager.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ch.hsr.smartmanager.data.Configuration;

public interface ConfigurationItemRepository extends MongoRepository<Configuration, String>{
	
}
