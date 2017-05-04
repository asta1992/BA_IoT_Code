package ch.hsr.smartmanager.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="device")
public class DeviceGroup implements DeviceComponent {

	@Id
	private String id;
	
	private String name;

	@DBRef
	private List<DeviceComponent> deviceComponent = new ArrayList<DeviceComponent>();
	@DBRef
	private List<DeviceComponent> parentComponent = new ArrayList<DeviceComponent>();

	public DeviceGroup(String name) {
		this.name = name;
	}

	@Override
	public void add(DeviceComponent deviceComponent) {
		if (!isParent(deviceComponent)) {
			deviceComponent.addParent(this);
			this.deviceComponent.add(deviceComponent);
		}
		else return;
	}

	@Override
	public boolean isParent(DeviceComponent component) {
		if (this.parentComponent.isEmpty()) {
			return false;
		}
		if (this.parentComponent.contains(component)) {
			return true;
		} else {
			if(component instanceof Device) return false;
			if ((((DeviceGroup) component).parentComponent != null)) {
					for (DeviceComponent comp : ((DeviceGroup) component).deviceComponent) {
						return isParent(comp);
					}
			}
			return false;
		}

	}

	@Override
	public void remove(DeviceComponent deviceComponent) {
		deviceComponent.removeParent(this);
		this.deviceComponent.remove(deviceComponent);
	}

	@Override
	public String getName() {
		return name;
	}

	public void print(String abstand) {
		String parent = "None";
		if (parentComponent.size() > 0) {
			parent = "";
			for (DeviceComponent s : parentComponent) {
				parent += s.getName() + ", ";
			}
		}
		System.out.println(abstand + "Gruppe " + getName() + " Parent: " + parent);
		for (DeviceComponent dc : deviceComponent) {
			dc.print(abstand + "      ");// Einrückung
		}

	}

	@Override
	public void addParent(DeviceComponent deviceComponent) {
		parentComponent.add(deviceComponent);
	}

	@Override
	public void removeParent(DeviceComponent deviceComponent) {
		parentComponent.add(deviceComponent);

	}

	public List<DeviceComponent> getDeviceComponent() {
		return deviceComponent;
	}

	public void setDeviceComponent(List<DeviceComponent> deviceComponent) {
		this.deviceComponent = deviceComponent;
	}

	public List<DeviceComponent> getParentComponent() {
		return parentComponent;
	}

	public void setParentComponent(List<DeviceComponent> parentComponent) {
		this.parentComponent = parentComponent;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return "DeviceGroup [id=" + id + ", name=" + name+ "]";
	}
	
	

	
	
	

}
