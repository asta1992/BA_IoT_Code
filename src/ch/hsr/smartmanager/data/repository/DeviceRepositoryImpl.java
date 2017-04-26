package ch.hsr.smartmanager.data.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import ch.hsr.smartmanager.data.Device;

public class DeviceRepositoryImpl implements DeviceRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public boolean endpointExists(String endpoint) {
		Query query = new Query();
		query.addCriteria(Criteria.where("endpoint").is(endpoint));
		
		return mongoTemplate.exists(query, Device.class);
	}

	@Override
	public Device findOneEndpoint(String endpoint) {
		Query query = new Query();
		query.addCriteria(Criteria.where("endpoint").is(endpoint));	
		return mongoTemplate.findOne(query, Device.class);
	}

	@Override
	public void deleteEndpoint(String endpoint) {

		Query query = new Query();
		query.addCriteria(Criteria.where("endpoint").is(endpoint));
		mongoTemplate.remove(query, Device.class);		
	}


}
