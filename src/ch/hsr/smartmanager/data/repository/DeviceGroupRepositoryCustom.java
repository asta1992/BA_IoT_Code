package ch.hsr.smartmanager.data.repository;

import org.springframework.stereotype.Repository;

import ch.hsr.smartmanager.data.DeviceComponent;

@Repository
public interface DeviceGroupRepositoryCustom {
	
	public DeviceComponent getGroupByName(String name);

}
