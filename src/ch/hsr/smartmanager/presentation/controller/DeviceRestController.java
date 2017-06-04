package ch.hsr.smartmanager.presentation.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.leshan.ResponseCode;
import org.eclipse.leshan.core.response.ReadResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.DeviceGroup;
import ch.hsr.smartmanager.service.DeviceService;
import ch.hsr.smartmanager.service.GroupService;
import ch.hsr.smartmanager.service.LocationService;
import ch.hsr.smartmanager.service.lwm2m.LwM2MHandler;

@RestController
@RequestMapping("/devices")
public class DeviceRestController {


	@Autowired
	LwM2MHandler lwM2MHandler;
	
	@Autowired
	DeviceService deviceService;
	
	@Autowired
	GroupService groupService;
	
	@Autowired
	LocationService locationService;


	@RequestMapping(value = "/{id}/read/{objectId}/{objectInstanceId}/{resourceId}", method = RequestMethod.GET)
	public ReadResponse readResource(Model model, @PathVariable("id") String id, @PathVariable("objectId") int objectId,
			@PathVariable("objectInstanceId") int objectInstanceId, @PathVariable("resourceId") int resourceId) {

		return lwM2MHandler.read(id, objectId, objectInstanceId, resourceId);
	}

	@RequestMapping(value = "/{id}/read/{objectId}", method = RequestMethod.GET)
	public ReadResponse readObject(Model model, @PathVariable("id") String id, @PathVariable("objectId") int objectId) {

		return lwM2MHandler.read(id, objectId);
	}

	@RequestMapping(value = "/{id}/write/{objectId}/{objectInstanceId}/{resourceId}", method = RequestMethod.POST)
	public Map<String, ResponseCode> write(Model model, @PathVariable("id") String id,
			@PathVariable("objectId") int objectId, @PathVariable("objectInstanceId") int objectInstanceId,
			@PathVariable("resourceId") int resourceId, @RequestParam("value") String value) {

		return lwM2MHandler.write(id, objectId, objectInstanceId, resourceId, value);
	}

	@RequestMapping(value = "/{id}/execute/{objectId}/{objectInstanceId}/{resourceId}", method = RequestMethod.GET)
	public Map<String, ResponseCode> execute(Model model, @PathVariable("id") String id, @PathVariable("objectId") int objectId,
			@PathVariable("objectInstanceId") int objectInstanceId, @PathVariable("resourceId") int resourceId) {

		return lwM2MHandler.execute(id, objectId, objectInstanceId, resourceId);
	}
	
	@RequestMapping(value = "/{id}/changeMembership", method = RequestMethod.POST)
	public void addToGroups(Model model, @PathVariable("id") String id, @RequestParam("value") JSONArray value) {
		List<DeviceGroup> postGroups = new ArrayList<>();
		for (int i = 0; i < value.length(); i++) {
			try {
				postGroups.add(groupService.getGroup(value.getString(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		Device device = deviceService.getDevice(id);
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
	
	@RequestMapping(value = "/locations/dashboard", method = RequestMethod.GET)
	public List<List<String>> getAllLocation(Model model) {
		return locationService.getAllLocation();
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
	public void removeDevice(Model model, @PathVariable("id") String id) {
		deviceService.removeFromManagement(id);
	}
	
	@RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
	public void removeDevice(Model model) {
		deviceService.deleteUnreachableDevices();
	}
	

	@RequestMapping(value = "/{id}/removeFromGroups", method = RequestMethod.POST)
	public void removeFromGroups(Model model, @PathVariable("id") String id,
			@RequestParam("value") List<String> value) {
		for (String groupId : value) {
			groupService.removeDeviceFromGroup(groupId, id);
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addDevice(Model model, @RequestParam("groupId") String groupId, @RequestParam("configId") String configId, @RequestParam(value = "deviceIds[]") String[] deviceIds) {
		deviceService.addToManagement(deviceIds, groupId, configId);
	}
	
	@RequestMapping(value = "/locations/{mapType}/{id}", method = RequestMethod.GET)
	public List<List<String>> getAllLocation(Model model, @PathVariable("id") String id,
			@PathVariable("mapType") String mapType) {
		if (mapType.equals("group")) {
			return locationService.getAllLocationByGroup(groupService.getGroup(id));
		} else {
			return locationService.getDeviceLocationById(deviceService.getDevice(id));
		}
	}
}
