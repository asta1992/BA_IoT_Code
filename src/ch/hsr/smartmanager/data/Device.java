package ch.hsr.smartmanager.data;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Device implements DeviceComponent {

	@Id
	private String id;

	private String name;
	private String regId;
	private String endpoint;
	private Date lastUpdate = new Date();
	private String latitude;
	private String longitude;
	private Date lastRegistrationUpdate;
	private TreeSet<String> objectLinks;
	private boolean added;
	private Map<String, String> dataMap;

	public Device() {
	}

	public Device(String name, String regId, String endpoint, TreeSet<String> objectLinks, Date lastRegistrationUpdate, boolean added) {
		this.name = name;
		this.regId = regId;
		this.endpoint = endpoint;
		this.objectLinks = objectLinks;
		this.lastRegistrationUpdate = lastRegistrationUpdate;
		this.added = added;
		this.setDataMap(new HashMap<>());
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

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public boolean isAdded() {
		return added;
	}

	public void setAdded(boolean added) {
		this.added = added;
	}

	public TreeSet<String> getObjectLinks() {
		return objectLinks;
	}

	public void setObjectLinks(TreeSet<String> objectLinks) {
		this.objectLinks = objectLinks;
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
	public List<DeviceComponent> getChildren() {
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (added ? 1231 : 1237);
		result = prime * result + ((endpoint == null) ? 0 : endpoint.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((objectLinks == null) ? 0 : objectLinks.hashCode());
		result = prime * result + ((regId == null) ? 0 : regId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Device other = (Device) obj;
		if (added != other.added)
			return false;
		if (endpoint == null) {
			if (other.endpoint != null)
				return false;
		} else if (!endpoint.equals(other.endpoint))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public Map<String, String> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, String> dataMap) {
		this.dataMap = dataMap;
	}

	public List<String> getObjectLinksDiv() {
		List<String> divLinks = new LinkedList<>();
		
		for(String s : this.getObjectLinks()) {
			divLinks.add(s.replace("/", ""));
		}
		return divLinks;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Date getLastRegistrationUpdate() {
		return lastRegistrationUpdate;
	}

	public void setLastRegistrationUpdate(Date lastRegistrationUpdate) {
		this.lastRegistrationUpdate = lastRegistrationUpdate;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}
