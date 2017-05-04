package ch.hsr.smartmanager.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ch.hsr.smartmanager.data.DeviceGroup;

@Repository
public interface DeviceGroupRepository extends MongoRepository<DeviceGroup, String>, DeviceGroupRepositoryCustom {
	
	

}
