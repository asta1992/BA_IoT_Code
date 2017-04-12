package ch.hsr.smartmanager.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ch.hsr.smartmanager.data.Credential;

public interface CredentialRepository extends MongoRepository<Credential, String>  {

}
