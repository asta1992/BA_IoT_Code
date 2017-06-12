package ch.hsr.smartmanager.data.repositories;

import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepositoryCustom {

	void removeDeviceByAddedIsFalse();
	void removeDeviceByName(String name);
}