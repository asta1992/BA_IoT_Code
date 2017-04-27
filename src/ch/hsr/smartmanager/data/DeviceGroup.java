package ch.hsr.smartmanager.data;

import java.util.ArrayList;
import java.util.List;

public class DeviceGroup implements DeviceComponent {

	private String name;

	List<DeviceComponent> deviceComponent = new ArrayList<DeviceComponent>();
	List<DeviceComponent> parentComponent = new ArrayList<DeviceComponent>();


	public DeviceGroup(String name) {
		this.name = name;
	}

	@Override
	public void add(DeviceComponent deviceComponent) {
		deviceComponent.addParent(this);
		this.deviceComponent.add(deviceComponent);
	}

	@Override
	public void remove(DeviceComponent deviceComponent) {
		deviceComponent.removeParent(this);
		this.deviceComponent.remove(deviceComponent);
	}

	@Override
	public DeviceComponent getChild(int index) {
		return deviceComponent.get(index);
	}

	@Override
	public String getName() {
		return name;
	}

	public void print(String abstand) {
		String parent = "None";
		if (parentComponent.size() > 0) {
			parent = "";
			for(DeviceComponent s : parentComponent) {
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


}
