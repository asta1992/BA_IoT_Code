package ch.hsr.smartmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hsr.smartmanager.data.AuthType;
import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.ProtocolType;
import ch.hsr.smartmanager.data.repository.DeviceRepository;

@Service("deviceService")
public class DeviceService {

	@Autowired
	private DeviceRepository repository;
	
	public AuthType[] getAuthType() {
		return AuthType.values();
	}
	
	public ProtocolType[] getProtocolType() {
		return ProtocolType.values();
	}
	
	public Device getDevice(String id) {
		return repository.findOne(id);
	}
	
	public List<Device> getAllDevice() {
		return repository.findAll();
	}

	public void deleteDevice(String id) {
		repository.delete(id);
	}

	public void createOrUpdateDevice(Device device) {
		repository.insert(device);
	}
	
}
