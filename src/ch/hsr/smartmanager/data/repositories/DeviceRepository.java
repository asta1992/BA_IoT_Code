package ch.hsr.smartmanager.data.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ch.hsr.smartmanager.data.Device;

@Repository
public interface DeviceRepository extends MongoRepository<Device, String>, DeviceRepositoryCustom {
	
	List<Device> findByAdded(boolean added);
	boolean existsByName(String name);
	Device findByName(String name);
}
