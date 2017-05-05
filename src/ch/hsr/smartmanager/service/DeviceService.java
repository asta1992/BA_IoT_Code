package ch.hsr.smartmanager.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.leshan.server.registration.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.DeviceComponent;
import ch.hsr.smartmanager.data.DeviceGroup;
import ch.hsr.smartmanager.data.repository.DeviceGroupRepository;
import ch.hsr.smartmanager.data.repository.DeviceRepository;

@Service("deviceService")
public class DeviceService {


	@Autowired
	private DeviceRepository deviceRepo;
	@Autowired
	private DeviceGroupRepository groupRepo;

	public void addDeviceToGroup(String to, String from) {
		DeviceGroup grpTo = groupRepo.findOne(to);
		Device devFrom = deviceRepo.findOne(from);
		grpTo.add(devFrom);
		groupRepo.save(grpTo);
		deviceRepo.save(devFrom);
	}
	
	public void addGroupToGroup(String to, String from) {
		DeviceGroup grpTo = groupRepo.findOne(to);
		DeviceGroup grpFrom = groupRepo.findOne(from);
		grpTo.add(grpFrom);
		groupRepo.save(grpTo);
		groupRepo.save(grpFrom);

	}

	public void addManyToGroup(List<String> devIds, String groupId) {
		DeviceGroup grp = (DeviceGroup) groupRepo.findOne(groupId);

		for (String id : devIds) {
			grp.add(deviceRepo.findOne(id));
		}
	}

	public Device getDevice(String id) {
		return deviceRepo.findOne(id);
	}

	public List<Device> getAllDiscoveredDevice() {
		return deviceRepo.findAllDevices(false);
	}

	public List<Device> getAllRegistredDevice() {
		return deviceRepo.findAllDevices(true);
	}
	
	public List<Device> getAll() {
		return deviceRepo.findAll();
	}

	public void deleteDevice(String id) {
		deviceRepo.delete(id);
	}

	public void deleteDevice(Registration registration) {
		deviceRepo.deleteEndpoint(registration.getEndpoint());
	}
	
	public Device insertDevice(Device device) {
		return deviceRepo.insert(device);
	}
	
	public DeviceGroup insertGroup(DeviceGroup grp) {
		return groupRepo.insert(grp);
	}

	public void createOrUpdateDevice(Device device, Registration registration) {
		if (deviceRepo.endpointExists(device.getName())) {
			deviceRepo.updateDevice(device, registration);
		} else {
			deviceRepo.insert(device);
		}
	}

	public void toggleDevice(String id) {
		deviceRepo.toggleDevice(id);
	}

	@PostConstruct
	public void removeOldDiscoveries() {
		deviceRepo.deleteUnusedDiscoveries();
	}

	public int countDiscoveredDevices() {
		return getAllDiscoveredDevice().size();
	}

}
