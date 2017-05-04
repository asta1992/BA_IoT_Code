package ch.hsr.smartmanager.data.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import ch.hsr.smartmanager.data.DeviceGroup;

public class DeviceGroupRepositoryImpl implements DeviceGroupRepositoryCustom {


	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public DeviceGroup getGroupByName(String name) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		return mongoTemplate.findOne(query, DeviceGroup.class);
	}

}
