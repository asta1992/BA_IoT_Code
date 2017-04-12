package ch.hsr.smartmanager.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ch.hsr.smartmanager.data.DeviceGroup;

public interface DeviceGroupRepository  extends MongoRepository<DeviceGroup, String>  {

}
