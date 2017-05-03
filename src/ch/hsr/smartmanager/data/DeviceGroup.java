package ch.hsr.smartmanager.data;

import java.util.HashSet;

public class DeviceGroup implements DeviceComponent {

	private String name;

	HashSet<DeviceComponent> deviceComponent = new HashSet<DeviceComponent>();
	HashSet<DeviceComponent> parentComponent = new HashSet<DeviceComponent>();
	
	


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
			System.out.println(dc.getName() + " ");
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
