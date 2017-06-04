package ch.hsr.smartmanager.data;

import java.util.List;

public interface DeviceComponent {
	
	public String getId();
	public String getName();
	public void add(DeviceComponent deviceComponent);
	public void remove(DeviceComponent deviceComponent);
	public List<DeviceComponent> getChildren();
	public boolean equals(Object obj);
	
}
