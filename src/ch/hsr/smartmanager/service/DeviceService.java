package ch.hsr.smartmanager.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
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

	public void addDeviceToGroup(String groupId, String deviceId) {
		DeviceGroup group = groupRepo.findOne(groupId);
		Device device = deviceRepo.findOne(deviceId);
		group.add(device);
		groupRepo.save(group);
	}

	public void addGroupToGroup(String parent, String child) {
		DeviceGroup grpParent = groupRepo.findOne(parent);
		DeviceGroup grpChild = groupRepo.findOne(child);
		
		if(isAncestors(grpParent.getName(), grpChild.getName())) return;
		
		if (!groupRepo.existsByChildrenId(new ObjectId(grpChild.getId()))) {
			grpParent.add(grpChild);
			groupRepo.save(grpParent);
			groupRepo.save(grpChild);
		}
	}
	
	private boolean isAncestors(String parent, String child) {
		List<String> anchestors = groupRepo.findAllAncestors("child");
		if(anchestors.contains(parent)) return true;
		return false;
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
		DeviceGroup group = (DeviceGroup) groupRepo.findByName("_unassigned");
		Device device = deviceRepo.findOne(id);
		device.setAdded(true);
		deviceRepo.save(device);
		addDeviceToGroup(group.getId(), id);
	}

	public void removeFromManagement(String id) {
		Device device = deviceRepo.findOne(id);
		List<DeviceGroup> groups = groupRepo.findAllByChildrenId(new ObjectId(id));
		for (DeviceGroup group : groups) {
			group.getChildren().remove(device);
		}
		groupRepo.save(groups);
		deviceRepo.delete(device);
	}

	public DeviceGroup findByName(String name) {
		return groupRepo.findByName(name);
	}

	public Device getDevice(String id) {
		return deviceRepo.findOne(id);
	}

	public DeviceGroup getGroup(String id) {
		return groupRepo.findOne(id);
	}
	
	public DeviceComponent getComponent(String id) {
		return groupRepo.findOne(id);
	}

	public List<DeviceGroup> getAllGroups() {
		return groupRepo.findAll();
	}

	public boolean isRoot(String id) {
		return groupRepo.existsByChildrenId(new ObjectId(id));
	}

	public Device insertDevice(Device device) {
		if (deviceRepo.existsByName(device.getName())) {
			return deviceRepo.findByName(device.getName());
		} else {
			return deviceRepo.insert(device);
		}
	}

	public DeviceGroup insertGroup(DeviceGroup group) {
		if (groupRepo.existsByName(group.getName())) {
			return groupRepo.findByName(group.getName());
		} else {
			return groupRepo.insert(group);
		}
	}

	public void deleteDevice(String id) {
		Device device = deviceRepo.findOne(id);
		for (DeviceGroup group : groupRepo.findAllByChildrenId(new ObjectId(id))) {
			group.getChildren().remove(device);
		}
		deviceRepo.delete(id);
	}

	public boolean deleteGroup(String id) {
		DeviceGroup group = groupRepo.findOne(id);
		if (!group.getChildren().isEmpty()) {
			return false;
		}
		groupRepo.delete(id);
		return true;
	}

	public void deleteDeviceByRegistration(Registration registration) {
		deviceRepo.removeDeviceByName(registration.getEndpoint());
	}

	public List<DeviceGroup> listAllGroupsForComponents(String id) {
		return groupRepo.findAllByChildrenId(new ObjectId(id));
	}

	public List<Device> getAllDiscoveredDevice() {
		return deviceRepo.findByAdded(false);
	}

	public List<Device> getAllRegistredDevice() {
		return deviceRepo.findByAdded(true);
	}

	public List<Device> getAllDevices() {
		return deviceRepo.findAll();
	}
	
	public List<DeviceComponent> getAllComponents() {
		List<DeviceComponent> allComponents = new ArrayList<DeviceComponent>();
		for(DeviceComponent component : this.getAllDevices()){
			allComponents.add(component);
		}
		for(DeviceComponent component : this.getAllGroups()) {
			allComponents.add(component);
		}
		
		return allComponents;
	}
	
	public List<DeviceGroup> findAllGroupById(List<String> id) {
		return groupRepo.findAllById(id);
	}

	public void createOrUpdateDevice(Device device, Registration registration) {
		if (deviceRepo.existsByName(device.getName())) {
			Device dev = deviceRepo.findByName(device.getName());
			dev.setRegId(registration.getId());
			dev = deviceRepo.save(dev);
		} else {
			deviceRepo.insert(device);
		}
	}

	public int countDiscoveredDevices() {
		return getAllDiscoveredDevice().size();
	}

	@PostConstruct
	public void removeOldDiscoveries() {
		if (!groupRepo.existsByName("_unassigned")) {
			DeviceGroup unassigned = new DeviceGroup("_unassigned");
			groupRepo.save(unassigned);
		}
		deviceRepo.removeDeviceByAddedIsFalse();
	}

}
