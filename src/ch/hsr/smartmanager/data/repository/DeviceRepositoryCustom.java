package ch.hsr.smartmanager.data.repository;

import org.springframework.stereotype.Repository;

import ch.hsr.smartmanager.data.Device;

@Repository
public interface DeviceRepositoryCustom {

	public boolean endpointExists(String endpoint);
	
	public Device findOneEndpoint(String endpoint);
	
	public void deleteEndpoint(String endpoint);
}
