package ch.hsr.smartmanager.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ch.hsr.smartmanager.data.Credential;

@Repository
public interface CredentialRepository extends MongoRepository<Credential, String>  {

}
