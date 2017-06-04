package ch.hsr.smartmanager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.bson.types.ObjectId;
import org.eclipse.leshan.ResponseCode;
import org.eclipse.leshan.core.node.LwM2mSingleResource;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.server.registration.Registration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.DeviceComponent;
import ch.hsr.smartmanager.data.DeviceGroup;
import ch.hsr.smartmanager.data.repository.DeviceGroupRepository;
import ch.hsr.smartmanager.data.repository.DeviceRepository;
import ch.hsr.smartmanager.service.lwm2m.LwM2MHandler;

@Service
public class DeviceService {

	@Autowired
	private DeviceRepository deviceRepo;
	@Autowired
	private DeviceGroupRepository groupRepo;
	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private LwM2MHandler lwM2MHandler;
	@Autowired
	private GroupService groupService;

	public void addToManagement(String[] deviceIds, String groupId, String configId) {
		DeviceGroup group;
		if (groupId.equals("_unassigned")) {
			group = groupRepo.findByName("_unassigned");
		} else {
			group = groupRepo.findOne(groupId);
		}

		for (String id : deviceIds) {
			Device device = deviceRepo.findOne(id);

			if (!configId.equals("none")) {
				configurationService.writeConfigurationToDevice(id, configId);
			}

			device.setAdded(true);
			
			deviceRepo.save(device);
			groupService.addDeviceToGroup(group.getId(), id);
		}
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

	public Device getDevice(String id) {
		return deviceRepo.findOne(id);
	}

	public void deleteDeviceByRegistration(Registration registration) {
		deviceRepo.removeDeviceByName(registration.getEndpoint());
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

	public Device updateDevice(Device device) {
		return deviceRepo.save(device);
	}

	public void createOrUpdateDevice(Device device, Registration registration) {
		Device dev;
		if (deviceRepo.existsByName(device.getName())) {
			dev = deviceRepo.findByName(device.getName());
			dev.setRegId(registration.getId());
			dev.setLastRegistrationUpdate(registration.getLastUpdate());
		} else {
			dev = deviceRepo.insert(device);
		}
		dev = deviceRepo.save(dev);

		if (dev.getObjectLinks().contains("/6/0")) {
			ReadResponse latitude = lwM2MHandler.read(dev.getId(), 6, 0, 0);
			ReadResponse longitude = lwM2MHandler.read(dev.getId(), 6, 0, 1);
			if (latitude != null && latitude.getCode() == ResponseCode.CONTENT) {
				LwM2mSingleResource resource = (LwM2mSingleResource) latitude.getContent();

				dev.setLatitude(resource.getValue().toString());
			}
			if (longitude != null && longitude.getCode() == ResponseCode.CONTENT) {
				LwM2mSingleResource resource = (LwM2mSingleResource) longitude.getContent();
				dev.setLongitude(resource.getValue().toString());
			}
		}
		dev = deviceRepo.save(dev);
	}

	public int countDiscoveredDevices() {
		return getAllDiscoveredDevice().size();
	}

	public int countAllDevices() {
		return getAllDevices().size() - getAllDiscoveredDevice().size();
	}

	public void removeDiscoveredDevices() {
		deviceRepo.removeDeviceByAddedIsFalse();
	}

	public List<Device> getUnreachableDevices() {
		long MAX_DURATION = TimeUnit.MILLISECONDS.convert(30, TimeUnit.MINUTES);
		List<Device> allDevices = deviceRepo.findAll();
		List<Device> unreachableDevices = new ArrayList<>();
		for (Device device : allDevices) {
			long duration = new Date().getTime() - device.getLastRegistrationUpdate().getTime();
			if (duration >= MAX_DURATION) {
				unreachableDevices.add(device);
			}
		}
		return unreachableDevices;
	}

	public void deleteUnreachableDevices() {
		List<Device> unreachableDevices = getUnreachableDevices();

		for (Device device : unreachableDevices) {
			removeFromManagement(device.getId());
		}

	}

	public List<JSONObject> allChildren(DeviceComponent deviceComponent) throws JSONException {
		List<JSONObject> jsonObjects = new ArrayList<>();
		for (DeviceComponent item : deviceComponent.getChildren()) {
			JSONObject jsonObj = new JSONObject();
			if (item instanceof Device) {
				jsonObj.put("id", "devices/" + item.getId());
				jsonObj.put("text", item.getName());
				jsonObjects.add(jsonObj);
			} else {
				item = groupService.getGroup(item.getId());

				if (item.getChildren().isEmpty()) {
					jsonObj.put("id", "groups/" + item.getId());
					jsonObj.put("text", item.getName());
					jsonObjects.add(jsonObj);
				} else {
					jsonObj.put("id", "groups/" + item.getId());
					jsonObj.put("text", item.getName());
					jsonObj.put("children", allChildren(item));
					jsonObjects.add(jsonObj);
				}
			}
		}
		return jsonObjects;
	}

	public void changeMembership(String id, JSONArray value) {
		List<DeviceGroup> postGroups = new ArrayList<>();
		for (int i = 0; i < value.length(); i++) {
			try {
				postGroups.add(groupService.getGroup(value.getString(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		Device device = getDevice(id);
		List<DeviceGroup> preGroups = groupService.listAllGroupsForGroup(id);

		for (DeviceGroup devGroups : preGroups) {
			if (!postGroups.contains(devGroups)) {
				groupService.removeDeviceFromGroup(devGroups.getId(), device.getId());
			}
		}
		for (DeviceGroup devGroups : postGroups) {
			if (!preGroups.contains(devGroups)) {
				groupService.addDeviceToGroup(devGroups.getId(), device.getId());
			}
		}

		DeviceGroup devGroup = groupService.findByName("_unassigned");

		if (postGroups.size() > 1 && postGroups.contains(devGroup)) {
			groupService.removeDeviceFromGroup(devGroup.getId(), id);
		}
		if (postGroups.isEmpty()) {
			groupService.addDeviceToGroup(devGroup.getId(), id);
		}
		
	}

}
