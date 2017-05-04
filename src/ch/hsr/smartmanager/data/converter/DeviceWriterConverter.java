package ch.hsr.smartmanager.data.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.DeviceComponent;

@Component
public class DeviceWriterConverter implements Converter<Device, DBObject> {

	@Override
	public DBObject convert(Device dev) {
		DBObject dbObject = new BasicDBObject();
		BasicDBList parCompList = new BasicDBList();

		for(DeviceComponent devComp : dev.getParentComponent()) {
			parCompList.add(devComp);
		}
		
		dbObject.put("name", dev.getName());
		dbObject.put("regId", dev.getRegId());
		dbObject.put("endpoint", dev.getEndpoint());
		dbObject.put("username", dev.getUsername());
		dbObject.put("password", dev.getPassword());
		dbObject.put("added", dev.isAdded());
		dbObject.put("parentComponent", parCompList);
		dbObject.removeField("_class");
		return dbObject;
	}
}


