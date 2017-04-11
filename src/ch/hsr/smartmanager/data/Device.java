package ch.hsr.smartmanager.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="devices")
public class Device {
	
	@Id
	public String id;
	
	public String name;
	public String ipAddress;
	
	public Device() {};
	
	public Device(String name, String ipAddress) {
		this.name = name;
		this.ipAddress = ipAddress;
	}
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public String toString() {
		return "Device [id=" + id + ", name=" + name + ", ipAddress=" + ipAddress + "]";
	}
	
	
	

}
