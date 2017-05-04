package ch.hsr.smartmanager.data.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import ch.hsr.smartmanager.data.DeviceComponent;
import ch.hsr.smartmanager.data.DeviceGroup;

@Component
public class DeviceGroupWriterConverter implements Converter<DeviceGroup, DBObject> {

	@Override
	public DBObject convert(DeviceGroup group) {
		DBObject dbObject = new BasicDBObject();
		BasicDBList devCompList = new BasicDBList();
		BasicDBList parCompList = new BasicDBList();


		dbObject.put("name", group.getName());

		for(DeviceComponent devComp: group.getDeviceComponent()) {
			devCompList.add(devComp);
		}
		for(DeviceComponent devComp: group.getParentComponent()) {
			parCompList.add(devComp);
		}
		dbObject.put("deviceComponent", devCompList);
		dbObject.put("parentComponent", parCompList);
		dbObject.removeField("_class");
		return dbObject;
	}

}
