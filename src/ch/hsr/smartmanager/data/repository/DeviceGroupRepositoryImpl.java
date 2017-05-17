package ch.hsr.smartmanager.data.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


public class DeviceGroupRepositoryImpl implements DeviceGroupRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<String> findAllAncestors(String name) {

		DBObject graphLookup = new BasicDBObject();
		BasicDBObject lookupDetails = new BasicDBObject();

		lookupDetails.put("from", "deviceGroup");
		lookupDetails.put("startWith", "$name");
		lookupDetails.put("connectFromField", "name");
		lookupDetails.put("connectToField", "children.name");
		lookupDetails.put("as", "ancestors");
		graphLookup.put("$graphLookup", lookupDetails);

		DBObject match = new BasicDBObject("$match", new BasicDBObject("name", name));

		BasicDBObject inName = new BasicDBObject("name", "$$t.name");

		BasicDBObject mapDetails = new BasicDBObject();
		mapDetails.put("input", "$ancestors");
		mapDetails.put("as", "t");
		mapDetails.put("in", inName);

		DBObject map = new BasicDBObject("$map", mapDetails);

		DBObject reverseArray = new BasicDBObject("$reverseArray", map);

		DBObject ancestors = new BasicDBObject();
		ancestors.put("ancestors", reverseArray);

		DBObject project = new BasicDBObject();
		project.put("$addFields", ancestors);

		
		BasicDBObject filter = new BasicDBObject();
		filter.put("$project", new BasicDBObject("ancestorsname", "$ancestors.name"));
		
		List<DBObject> pipeline = Arrays.asList(project,match, graphLookup, filter);
		AggregationOutput output = mongoTemplate.getCollection("deviceGroup").aggregate(pipeline);

		JSONArray jsonArray = new JSONArray();
		List<String> list = new ArrayList<>();
		for(DBObject a : output.results()) {
			try {
				jsonArray = new JSONArray(a.get("ancestorsname").toString());
				for (int i=0; i<jsonArray.length(); i++) {
					list.add( jsonArray.getString(i) );
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return list;

	}
}
