package ch.hsr.smartmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hsr.smartmanager.data.Credential;
import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.Type;
import ch.hsr.smartmanager.data.repository.CredentialRepository;
import ch.hsr.smartmanager.data.repository.DeviceRepository;
import ch.hsr.smartmanager.data.repository.TypeRepository;

@Service("deviceService")
public class DeviceService {

	@Autowired
	private DeviceRepository repository;
	@Autowired
	private TypeRepository typeRepository;
	@Autowired
	private CredentialRepository credentialRepository;

	
	public Device createDevice(Device device) {
		return repository.insert(device);
	}
	
	public Type createType(Type type) {
		return typeRepository.insert(type);
	}

	public Credential createCredential(Credential credential) {
		return credentialRepository.insert(credential);
	}
	
	
	public Device getDevice(String id) {
		return repository.findOne(id);
	}
	
	public List<Device> getAllDevice() {
		return repository.findAll();
	}
	
}
