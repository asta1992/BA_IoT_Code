package ch.hsr.smartmanager.data.repositories;

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

		AggregationOutput output = mongoTemplate.getCollection("deviceGroup")
				.aggregate(getLookupQuery("$name", "name", "children.name", name));

		JSONArray jsonArray = new JSONArray();
		List<String> list = new ArrayList<>();
		for (DBObject a : output.results()) {
			try {
				jsonArray = new JSONArray(a.get("ancestorsname").toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					list.add(jsonArray.getString(i));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return list;
	}
	
	@Override
	public List<String> findAllChildren(String name) {

		AggregationOutput output = mongoTemplate.getCollection("deviceGroup")
				.aggregate(getLookupQuery("$children.name", "children.name", "name", name));

		JSONArray jsonArray = new JSONArray();
		List<String> list = new ArrayList<>();
		for (DBObject a : output.results()) {
			try {
				jsonArray = new JSONArray(a.get("ancestorsname").toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					list.add(jsonArray.getString(i));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	public List<DBObject> getLookupQuery(String startWith, String connectFromField, String connectToField,
			String name) {
		DBObject graphLookup = new BasicDBObject();
		BasicDBObject lookupDetails = new BasicDBObject();

		lookupDetails.put("from", "deviceGroup");
		lookupDetails.put("startWith", startWith);
		lookupDetails.put("connectFromField", connectFromField);
		lookupDetails.put("connectToField", connectToField);
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

		return Arrays.asList(project, match, graphLookup, filter);

	}
}
