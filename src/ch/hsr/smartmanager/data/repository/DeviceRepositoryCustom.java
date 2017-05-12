package ch.hsr.smartmanager.data.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepositoryCustom {

	void removeDeviceByAddedIsFalse();

	void removeDeviceByName(String name);
	void removeDeviceFromAllGroups(String deviceId);

}