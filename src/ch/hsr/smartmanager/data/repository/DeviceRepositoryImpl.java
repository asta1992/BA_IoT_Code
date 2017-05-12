package ch.hsr.smartmanager.data.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.BulkUpdateRequestBuilder;

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.DeviceGroup;

public class DeviceRepositoryImpl implements DeviceRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void removeDeviceByAddedIsFalse() {
		Query query = new Query();
		query.addCriteria(Criteria.where("added").is(false));
		mongoTemplate.remove(query, DeviceGroup.class);
	}

	@Override
	public void removeDeviceByName(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		mongoTemplate.remove(query, DeviceGroup.class);
	}
	
	@Override
	public void removeDeviceFromAllGroups(String deviceId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("children._id").is(deviceId));
		mongoTemplate.remove(query, Device.class);
	}

}
