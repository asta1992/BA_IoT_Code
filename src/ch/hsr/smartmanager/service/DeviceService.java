package ch.hsr.smartmanager.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.eclipse.leshan.server.registration.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.DeviceGroup;
import ch.hsr.smartmanager.data.repository.DeviceGroupRepository;
import ch.hsr.smartmanager.data.repository.DeviceRepository;

@Service("deviceService")
public class DeviceService {

	@Autowired
	private DeviceRepository deviceRepo;
	@Autowired
	private DeviceGroupRepository groupRepo;

	public void addDeviceToGroup(String groupId, String deviceId) {
		DeviceGroup group = groupRepo.findOne(groupId);
		Device device = deviceRepo.findOne(deviceId);
		group.add(device);
		groupRepo.save(group);
		deviceRepo.save(device);
	}

	public void addGroupToGroup(String parent, String child) {
		DeviceGroup grpParent = groupRepo.findOne(parent);
		DeviceGroup grpChild = groupRepo.findOne(child);
		grpParent.add(grpChild);
		groupRepo.save(grpParent);
		groupRepo.save(grpChild);
	}
	
	public void removeDeviceFromGroup(String groupId, String deviceId) {
		DeviceGroup group = groupRepo.findOne(groupId);
		Device device = deviceRepo.findOne(deviceId);
		group.remove(device);
		groupRepo.save(group);
		deviceRepo.save(device);
	}
	
	public void removeGroupFromGroup(String parent, String child) {
		DeviceGroup grpParent = groupRepo.findOne(parent);
		DeviceGroup grpChild = groupRepo.findOne(child);
		grpParent.remove(grpChild);
		groupRepo.save(grpParent);
		groupRepo.save(grpChild);
	}

	public void addToManagement(String id) {
		DeviceGroup group = (DeviceGroup) groupRepo.getGroupByName("_unassigned");
		Device device = deviceRepo.findOne(id);
		device.setAdded(true);
		deviceRepo.save(device);
		addDeviceToGroup(group.getId(), id);
	}
	
//	public void removeFromManagement(String id) {
//		Device device = deviceRepo.findOne(id);
//		List<DeviceGroup> groups = groupRepo.findAllByChildrenId(new ObjectId(id));
//		for(DeviceGroup group: groups) {
//			group.getChildren().remove(device);
//		}
//		groupRepo.save(groups);
//		deviceRepo.delete(device);
//	}
//	

	public Device getDevice(String id) {
		return deviceRepo.findOne(id);
	}

	public DeviceGroup getGroup(String id) {
		return groupRepo.findOne(id);
	}
	
	public List<DeviceGroup> getGroupAll() {
		return groupRepo.findAll();
	}
	
	public boolean isRoot(String id) {
		return groupRepo.isRoot(id);
	}


	public Device insertDevice(Device device) {
		return deviceRepo.insert(device);
	}

	public DeviceGroup insertGroup(DeviceGroup grp) {
		return groupRepo.insert(grp);
	}

	public void deleteDevice(String id) {
		deviceRepo.delete(id);
	}

	public void deleteDeviceByRegistration(Registration registration) {
		deviceRepo.removeDeviceByName(registration.getEndpoint());
	}

	public void deleteGroup(String id) {
		groupRepo.delete(id);
	}
	
	public List<DeviceGroup> listAllGroupsForDevice(String id) {
		return groupRepo.getGroupsForDevice(id);
	}

	public List<Device> getAllDiscoveredDevice() {
		return deviceRepo.findByAdded(false);
	}

	public List<Device> getAllRegistredDevice() {
		return deviceRepo.findByAdded(true);
	}

	public List<Device> getAll() {
		return deviceRepo.findAll();
	}

	public void createOrUpdateDevice(Device device, Registration registration) {
		if (deviceRepo.existsByName(device.getName())) {
			Device dev = deviceRepo.findOne(device.getId());
			dev.setRegId(registration.getId());
			deviceRepo.save(dev);
		} else {
			deviceRepo.insert(device);
		}
	}

	public int countDiscoveredDevices() {
		return getAllDiscoveredDevice().size();
	}

	@PostConstruct
	public void removeOldDiscoveries() {
		if(!groupRepo.existsByName("_unassigned")) {
			DeviceGroup unassigned = new DeviceGroup("_unassigned");
			groupRepo.save(unassigned);
		}
		deviceRepo.removeDeviceByAddedIsFalse();
	}

}
