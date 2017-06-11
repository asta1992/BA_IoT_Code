package ch.hsr.smartmanager.data.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import ch.hsr.smartmanager.data.ManagementUser;

public class ManagementUserRepositoryImpl implements ManagementUserRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public void removeByUsername(String username) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		mongoTemplate.remove(query, ManagementUser.class);		
	}

}
