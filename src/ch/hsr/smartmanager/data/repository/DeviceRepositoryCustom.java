package ch.hsr.smartmanager.data.repository;

import java.util.List;

import org.eclipse.leshan.server.registration.Registration;
import org.springframework.stereotype.Repository;

import ch.hsr.smartmanager.data.Device;

@Repository
public interface DeviceRepositoryCustom {

	public boolean endpointExists(String name);
		
	public void deleteEndpoint(String name);
	
	public void updateDevice(Device device, Registration registration);
		
	public List<Device> findAllDevices(boolean added);
	
	public void toggleDevice(String id);
	
	public void deleteUnusedDiscoveries();
	
	public Device getDeviceByName(String name);

	public Device findOneDevice(String id);
	


}
