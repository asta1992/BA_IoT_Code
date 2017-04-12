package ch.hsr.smartmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.repository.DeviceRepository;

@Service("deviceService")
public class DeviceService {
	
	private DeviceRepository repository;
		
	@Autowired
	public void setRepository(DeviceRepository repository) {
		this.repository = repository;
	}
	
	public void createDevice(Device device) {
		repository.insert(device);
	}
	
	public Device getDevice(String id) {
		return repository.findOne(id);
	}
	
	public List<Device> getAllDevice() {
		return repository.findAll();
	}
	
}
