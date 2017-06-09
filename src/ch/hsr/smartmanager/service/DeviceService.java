package ch.hsr.smartmanager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.eclipse.leshan.ResponseCode;
import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.ResourceModel;
import org.eclipse.leshan.core.node.LwM2mMultipleResource;
import org.eclipse.leshan.core.node.LwM2mObject;
import org.eclipse.leshan.core.node.LwM2mObjectInstance;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.node.LwM2mSingleResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.eclipse.leshan.server.model.StandardModelProvider;
import org.eclipse.leshan.server.registration.Registration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.DeviceComponent;
import ch.hsr.smartmanager.data.DeviceGroup;
import ch.hsr.smartmanager.data.ResourceModelAdapter;
import ch.hsr.smartmanager.data.repository.DeviceGroupRepository;
import ch.hsr.smartmanager.data.repository.DeviceRepository;
import ch.hsr.smartmanager.service.lwm2m.LwM2MHandler;
import ch.hsr.smartmanager.service.lwm2m.LwM2MManagementServer;

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
	@Autowired
	private LwM2MManagementServer lwM2MManagementServer;

	public ReadResponse read(String id, int objectId, int objectInstanceId, int resourceId) {
		Device device = getDevice(id);
		ReadResponse res = lwM2MHandler.read(device, objectId, objectInstanceId, resourceId);
		

		if (res != null && res.getCode() != ResponseCode.NOT_FOUND) {
			Device dev = saveValueToDevice(res, device, Integer.toString(objectId) + Integer.toString(objectInstanceId) + Integer.toString(resourceId));
			updateDevice(dev);
		}
		
		return res;

	}

	public ReadResponse read(String id, int objectId) {
		Device device = getDevice(id);

		ReadResponse res = lwM2MHandler.read(device, objectId);

		if (res != null) {
			Device dev = saveMultipleValueToDevice(res, device, objectId);
			updateDevice(dev);
		}
		return res;

	}

	public Map<String, ResponseCode> write(String id, int objectId, int objectInstanceId, int resourceId,
			String value) {

		Map<String, ResponseCode> response = new HashMap<>();
		Device device = getDevice(id);

		if (device == null) {
			response.put(objectId + "/" + objectInstanceId + "/" + resourceId, ResponseCode.NOT_FOUND);
			return response;
		}

		WriteResponse res = lwM2MHandler.write(device, objectId, objectInstanceId, resourceId, value);

		if (res != null && res.getCode() == ResponseCode.CHANGED) {
			read(device.getId(), objectId, objectInstanceId, resourceId);
			response.put(objectId + "/" + objectInstanceId + "/" + resourceId, res.getCode());
		}
		else {
			response.put(objectId + "/" + objectInstanceId + "/" + resourceId, ResponseCode.NOT_FOUND);
		}

		return response;
	}

	public Map<String, ResponseCode> execute(String id, int objectId, int objectInstanceId, int resourceId) {
		Device device = getDevice(id);
		Map<String, ResponseCode> response = new HashMap<>();

		if (device == null) {
			response.put(objectId + "/" + objectInstanceId + "/" + resourceId, ResponseCode.NOT_FOUND);
			return response;
		}

		ExecuteResponse res = lwM2MHandler.execute(device, objectId, objectInstanceId, resourceId);

		if (res != null) {
			response.put(objectId + "/" + objectInstanceId + "/" + resourceId, res.getCode());
			return response;
		}
		else {
			response.put(objectId + "/" + objectInstanceId + "/" + resourceId, ResponseCode.NOT_FOUND);
		}
		return response;
	}

	public Map<String, List<Map<String, ResponseCode>>> writeToAllChildren(List<Device> devices, int objectId,
			int objectInstanceId, int resourceId, String value) {
		Map<String, List<Map<String, ResponseCode>>> responseMap = new HashMap<>();

		for (Device dev : devices) {
			if (dev != null) {
				responseMap.put(dev.getName(),
						writeConfigurationToDevice(dev, objectId, objectInstanceId, resourceId, value));
			}
		}
		return responseMap;

	}

	public Map<String, List<Map<String, ResponseCode>>> executeToAllChildren(List<Device> devices, int objectId,
			int objectInstanceId, int resourceId) {
		Map<String, List<Map<String, ResponseCode>>> responseMap = new HashMap<>();

		for (Device dev : devices) {
			if (dev != null) {
				responseMap.put(dev.getName(), executeToDevice(dev, objectId, objectInstanceId, resourceId));
			}
		}
		return responseMap;

	}

	public List<Map<String, ResponseCode>> writeConfigurationToDevice(Device device, int objectId, int objectInstanceId,
			int resourceId, String value) {
		List<Map<String, ResponseCode>> responseList = new ArrayList<>();

		responseList.add(write(device.getId(), objectId, objectInstanceId, resourceId, value));
		return responseList;
	}

	private List<Map<String, ResponseCode>> executeToDevice(Device device, int objectId, int objectInstanceId,
			int resourceId) {
		List<Map<String, ResponseCode>> responseList = new ArrayList<>();

		responseList.add(execute(device.getId(), objectId, objectInstanceId, resourceId));
		return responseList;
	}
	
	
	private Device saveMultipleValueToDevice(ReadResponse res, Device device, int objectId) {

		if (res.getContent() == null)
			return device;

		String path = Integer.toString(objectId);

		LwM2mObject node = (LwM2mObject) res.getContent();

		Map<Integer, LwM2mObjectInstance> instance = node.getInstances();
		Map<String, String> dataMap = device.getDataMap();

		for (Map.Entry<Integer, LwM2mObjectInstance> entry : instance.entrySet()) {
			Map<Integer, LwM2mResource> inst = entry.getValue().getResources();

			for (Map.Entry<Integer, LwM2mResource> resource : inst.entrySet()) {
				if (resource.getValue() instanceof LwM2mSingleResource) {
					LwM2mSingleResource singleRes = (LwM2mSingleResource) resource.getValue();

					dataMap.put(path + entry.getKey() + resource.getKey(), singleRes.getValue().toString());

				} else if (resource.getValue() instanceof LwM2mMultipleResource) {
					LwM2mMultipleResource resources = (LwM2mMultipleResource) resource.getValue();
					Map<Integer, ?> a = resources.getValues();
					dataMap.put(path, a.entrySet().toString());
				}
			}

		}

		device.setLastUpdate(new Date());
		device.setDataMap(dataMap);

		return device;
	}

	private Device saveValueToDevice(ReadResponse res, Device dev, String path) {

		Map<String, String> dataMap = dev.getDataMap();

		if (res.getContent() != null) {
			if (res.getContent() instanceof LwM2mSingleResource) {
				LwM2mSingleResource resource = (LwM2mSingleResource) res.getContent();

				dataMap.put(path, resource.getValue().toString());

			} else if (res.getContent() instanceof LwM2mMultipleResource) {
				LwM2mMultipleResource resources = (LwM2mMultipleResource) res.getContent();

				dataMap.put(path, resources.getValues().toString());
			}
		}
		dev.setLastUpdate(new Date());
		dev.setDataMap(dataMap);
		return dev;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

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

	public void removeNullDevice(String id) {
		List<DeviceGroup> groups = groupRepo.findAllByChildrenId(new ObjectId(id));
		for (DeviceGroup group : groups) {
			Iterator<DeviceComponent> iter = group.getChildren().iterator();
			while (iter.hasNext()) {
				DeviceComponent p = iter.next();
				if (p.getId().equals(id))
					iter.remove();
			}
			groupRepo.save(group);
		}
	}

	public Device getDevice(String id) {
		return deviceRepo.findOne(id);
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
			ReadResponse latitude = read(dev.getId(), 6, 0, 0);
			ReadResponse longitude = read(dev.getId(), 6, 0, 1);
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

	public LinkedHashMap<String, ArrayList<ResourceModelAdapter>> getObjectModelList(String id) {
		Device device = getDevice(id);
		LinkedHashMap<String, ArrayList<ResourceModelAdapter>> objectModelList = new LinkedHashMap<String, ArrayList<ResourceModelAdapter>>();
		ArrayList<ResourceModelAdapter> resourceModelList = new ArrayList<ResourceModelAdapter>();

		final String regex = "\\/([0-9]*)\\/";
		final Pattern pattern = Pattern.compile(regex);
		Matcher matcher;

		if (device == null) {
			return objectModelList;
		} else {
			LwM2mModel regModel;

			Registration registration = lwM2MManagementServer.getServer().getRegistrationService()
					.getById(device.getRegId());
			if (registration == null) {
				regModel = new StandardModelProvider().getObjectModel(registration);
			} else {
				regModel = lwM2MManagementServer.getServer().getModelProvider().getObjectModel(registration);
			}

			for (String objId : device.getObjectLinks()) {
				matcher = pattern.matcher(objId);
				String parseId = "1";
				if (matcher.find()) {
					parseId = matcher.group(1);
				}

				ObjectModel objectModel = regModel.getObjectModel(Integer.parseInt(parseId));
				resourceModelList = new ArrayList<ResourceModelAdapter>();

				for (ResourceModel entry : objectModel.resources.values()) {
					resourceModelList.add(new ResourceModelAdapter(entry));
				}
				objectModelList.put(objectModel.name, resourceModelList);
			}
			return objectModelList;
		}

	}

	public Registration getRegistration(String id) {
		Device device = deviceRepo.findOne(id);
		if (device == null) {
			return null;
		} else
			return lwM2MManagementServer.getServer().getRegistrationService().getById(device.getRegId());

	}

}
