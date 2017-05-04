package ch.hsr.smartmanager.data;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="device")
public interface DeviceComponent {
	
	public void add(DeviceComponent deviceComponent);
	public void remove(DeviceComponent deviceComponent);
	public String getName();
	public void print(String abstand);
	public void addParent(DeviceComponent deviceComponent);
	public void removeParent(DeviceComponent deviceComponent);
	public boolean isParent(DeviceComponent deviceComponent);
	public List<DeviceComponent> getParentComponent();
	public List<DeviceComponent> getDeviceComponent();
	public String getId();
	
}
