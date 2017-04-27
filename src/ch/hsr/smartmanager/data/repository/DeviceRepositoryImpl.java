package ch.hsr.smartmanager.data.repository;

import java.util.List;

import org.eclipse.leshan.server.registration.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import ch.hsr.smartmanager.data.Device;

public class DeviceRepositoryImpl implements DeviceRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public boolean endpointExists(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		
		return mongoTemplate.exists(query, Device.class);
	}

	@Override
	public void deleteEndpoint(String name) {

		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		mongoTemplate.remove(query, Device.class);		
	}
	
	@Override
	public void deleteUnusedDiscoveries() {
		Query query = new Query();
		query.addCriteria(Criteria.where("added").is(false));
		mongoTemplate.remove(query, Device.class);		
	}
	
	@Override
	public void updateDevice(Device device, Registration registration) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(device.getName()));
		Device dev = mongoTemplate.findOne(query, Device.class);	
		dev.setRegId(registration.getId());
		mongoTemplate.save(dev);
	}

	@Override
	public List<Device> findAllDevices(boolean added) {
		Query query = new Query();
		query.addCriteria(Criteria.where("added").is(added));
		return mongoTemplate.find(query, Device.class);

	}

	@Override
	public void toggleDevice(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("regId").is(id));
		Device dev = mongoTemplate.findOne(query, Device.class);	
		dev.setAdded(!dev.isAdded());
		mongoTemplate.save(dev);
		
	}


}
