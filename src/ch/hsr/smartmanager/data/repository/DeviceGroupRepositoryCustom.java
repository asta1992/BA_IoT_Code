package ch.hsr.smartmanager.data.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import ch.hsr.smartmanager.data.DeviceComponent;
import ch.hsr.smartmanager.data.DeviceGroup;

@Repository
public interface DeviceGroupRepositoryCustom {
	
	public DeviceComponent getGroupByName(String name);
	public List<DeviceGroup> getGroupsForDevice(String id);
	public boolean isRoot(String id);

}
