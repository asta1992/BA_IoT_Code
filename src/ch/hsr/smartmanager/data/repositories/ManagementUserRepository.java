package ch.hsr.smartmanager.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import ch.hsr.smartmanager.data.ManagementUser;

public interface ManagementUserRepository extends MongoRepository<ManagementUser, String>, ManagementUserRepositoryCustom {
	ManagementUser findByUsername(String username);
	boolean existsByUsername(String username);
}
