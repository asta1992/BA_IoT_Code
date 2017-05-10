package ch.hsr.smartmanager.data.repository;

import java.util.List;

import org.bson.types.ObjectId;
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
	
	public List<DeviceGroup> getGroupsForDevice(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("children._id").is(new ObjectId(id)));
		return mongoTemplate.find(query, DeviceGroup.class);
	}

}
