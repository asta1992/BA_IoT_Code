package ch.hsr.smartmanager.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ch.hsr.smartmanager.data.Type;

public interface TypeRepository  extends MongoRepository<Type, String>  {

}
