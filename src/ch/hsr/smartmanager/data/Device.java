package ch.hsr.smartmanager.data;

import java.util.HashSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "devices")
public class Device implements DeviceComponent {

	@Id
	private String id;

	private String name;
	private String regId;
	private String endpoint;
	private String username;
	private String password;
	private boolean added;
	private HashSet<DeviceComponent> parent = new HashSet<DeviceComponent>();

	public Device() {
	}

	public Device(String name) {
		this.name = name;
	}

	public Device(String name, String regId, String endpoint, String username, String password, boolean added) {
		this.name = name;
		this.regId = regId;
		this.endpoint = endpoint;
		this.username = username;
		this.password = password;
		this.added = added;
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

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEntpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public void addParent(DeviceComponent parentChild) {
		this.parent.add(parentChild);
	}

	public void removeParent(DeviceComponent parentChild) {
		this.parent.remove(parentChild);
	}

	public boolean isAdded() {
		return added;
	}

	public void setAdded(boolean added) {
		this.added = added;
	}

	@Override
	public void add(DeviceComponent deviceComponent) {
		// Leaf node. No Implementation needed
	}

	@Override
	public void remove(DeviceComponent deviceComponent) {
		// Leaf node. No Implementation needed
	}

	public boolean isNew() {
		return (this.id == null);
	}

	@Override
	public void print(String abstand) {
		String parentString = "None";
		if (parent.size() > 0) {
			parentString = "";
			for (DeviceComponent s : parent) {
				parentString += s.getName() + ", ";
			}
		}
		System.out.println(abstand + toString() + " Parent: " + parentString);
	}

	@Override
	public String toString() {
		return "Device [id=" + id + ", name=" + name + ", regId=" + regId + ", endpoint=" + endpoint + ", username="
				+ username + ", password=" + password + "]";
	}

}
