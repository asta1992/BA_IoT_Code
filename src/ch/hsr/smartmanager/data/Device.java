package ch.hsr.smartmanager.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="devices")
public class Device {
	
	@Id
	public String id;
	
	public String name;
	public Type type;
	public String ipAddress;
	
	
	public Device() {};
	
	public Device(String name, Type type, String ipAddress) {
		this.name = name;
		this.type = type;
		this.ipAddress = ipAddress;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public String toString() {
		return "Device [id=" + id + ", name=" + name + ", type=" + type + ", ipAddress="
				+ ipAddress + "]";
	}


	

	
	
	

}
