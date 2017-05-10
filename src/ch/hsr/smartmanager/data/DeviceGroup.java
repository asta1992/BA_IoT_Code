package ch.hsr.smartmanager.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DeviceGroup implements DeviceComponent {

	@Id
	private String id;

	private String name;

	private List<DeviceComponent> children = new ArrayList<DeviceComponent>();

	public DeviceGroup(String name) {
		this.name = name;
	}

	@Override
	public void add(DeviceComponent deviceComponent) {
		if(deviceComponent instanceof Device &&  (!children.contains(deviceComponent))){
			children.add(deviceComponent);
			return;
		}
		
		if (children.contains(deviceComponent)) {
			return;
		}
		else if (!isChild(deviceComponent) && !deviceComponent.isChild(this)) {
			this.children.add(deviceComponent);
		} else
			return;

	}

	@Override
	public boolean isChild(DeviceComponent component) {
		if (this.children.isEmpty()) {
			return false;
		}
		if (this.children.contains(component)) {
			return true;
		} else {
			for (DeviceComponent comp : children) {
				return isChild(comp);
			}
			return false;
		}
	}

	@Override
	public void remove(DeviceComponent deviceComponent) {
		this.children.remove(deviceComponent);
	}

	public void print(String abstand) {
		System.out.println(abstand + "Gruppe " + getName());
		for (DeviceComponent dc : children) {
			dc.print(abstand + "      ");
		}

	}

	public List<DeviceComponent> getChildren() {
		return children;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "DeviceGroup [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		DeviceGroup other = (DeviceGroup) obj;
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
	
	

}
