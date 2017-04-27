package ch.hsr.smartmanager.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.leshan.server.registration.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.repository.DeviceRepository;

@Service("deviceService")
public class DeviceService {

	@Autowired
	private DeviceRepository repository;

	public Device getDevice(String id) {
		return repository.findOne(id);
	}

	public List<Device> getAllDiscoveredDevice() {
		return repository.findAllDevices(false);
	}

	public List<Device> getAllRegistredDevice() {
		return repository.findAllDevices(true);
	}

	public void deleteDevice(String id) {
		repository.delete(id);
	}

	public void deleteDevice(Registration registration) {
		repository.deleteEndpoint(registration.getEndpoint());
	}

	public void createOrUpdateDevice(Device device, Registration registration) {
		if (repository.endpointExists(device.getName())) {
			repository.updateDevice(device, registration);
		} else {
			repository.insert(device);
		}
	}
	
	public void toggleDevice(String id) {
		repository.toggleDevice(id);
	}
	

	@PostConstruct
	public void removeOldDiscoveries() {
		repository.deleteUnusedDiscoveries();
	}

	public int countDiscoveredDevices() {
		return getAllDiscoveredDevice().size();
	}

}
