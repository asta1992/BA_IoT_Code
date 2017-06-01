package ch.hsr.smartmanager.data;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="device")
public interface DeviceComponent {
	
	public String getId();
	public String getName();
	public void add(DeviceComponent deviceComponent);
	public void remove(DeviceComponent deviceComponent);
	public List<DeviceComponent> getChildren();
	public boolean equals(Object obj);
	
}
