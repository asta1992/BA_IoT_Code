package ch.hsr.smartmanager.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="deviceGroup")
public class DeviceGroup {

	@Id
	public String id;
}
