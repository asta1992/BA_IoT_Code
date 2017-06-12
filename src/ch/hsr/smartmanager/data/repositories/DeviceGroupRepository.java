package ch.hsr.smartmanager.data.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ch.hsr.smartmanager.data.DeviceGroup;

@Repository
public interface DeviceGroupRepository extends MongoRepository<DeviceGroup, String>, DeviceGroupRepositoryCustom {

	DeviceGroup findByName(String name);

	boolean existsByName(String name);

	List<DeviceGroup> findAllByChildrenId(ObjectId id);
	DeviceGroup findByChildrenId(ObjectId id);


	boolean existsByChildrenId(ObjectId id);

}
