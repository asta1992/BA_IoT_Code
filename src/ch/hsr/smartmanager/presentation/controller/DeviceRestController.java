package ch.hsr.smartmanager.presentation.controller;

import java.util.List;
import java.util.Map;

import org.eclipse.leshan.ResponseCode;
import org.eclipse.leshan.core.response.ReadResponse;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public ReadResponse readResource(@PathVariable("id") String id, @PathVariable("objectId") int objectId,
			@PathVariable("objectInstanceId") int objectInstanceId, @PathVariable("resourceId") int resourceId) {

		return lwM2MHandler.read(id, objectId, objectInstanceId, resourceId);
	}

	@RequestMapping(value = "/{id}/read/{objectId}", method = RequestMethod.GET)
	public ReadResponse readObject(@PathVariable("id") String id, @PathVariable("objectId") int objectId) {

		return lwM2MHandler.read(id, objectId);
	}

	@RequestMapping(value = "/{id}/write/{objectId}/{objectInstanceId}/{resourceId}", method = RequestMethod.POST)
	public Map<String, ResponseCode> write(@PathVariable("id") String id,
			@PathVariable("objectId") int objectId, @PathVariable("objectInstanceId") int objectInstanceId,
			@PathVariable("resourceId") int resourceId, @RequestParam("value") String value) {

		return lwM2MHandler.write(id, objectId, objectInstanceId, resourceId, value);
	}

	@RequestMapping(value = "/{id}/execute/{objectId}/{objectInstanceId}/{resourceId}", method = RequestMethod.GET)
	public Map<String, ResponseCode> execute(@PathVariable("id") String id, @PathVariable("objectId") int objectId,
			@PathVariable("objectInstanceId") int objectInstanceId, @PathVariable("resourceId") int resourceId) {

		return lwM2MHandler.execute(id, objectId, objectInstanceId, resourceId);
	}
	
	@RequestMapping(value = "/{id}/changeMembership", method = RequestMethod.POST)
	public void addToGroups(@PathVariable("id") String id, @RequestParam("value") JSONArray value) {
		deviceService.changeMembership(id, value);
	}
	
	@RequestMapping(value = "/locations/dashboard", method = RequestMethod.GET)
	public List<List<String>> getAllLocation() {
		return locationService.getAllLocation();
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
	public void removeDevice(@PathVariable("id") String id) {
		deviceService.removeFromManagement(id);
	}
	
	@RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
	public void removeDevice() {
		deviceService.deleteUnreachableDevices();
	}
	

	@RequestMapping(value = "/{id}/removeFromGroups", method = RequestMethod.POST)
	public void removeFromGroups(@PathVariable("id") String id, @RequestParam("value") List<String> value) {
		groupService.removeDeviceFromGroups(value, id);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addDevice(@RequestParam("groupId") String groupId, @RequestParam("configId") String configId, @RequestParam(value = "deviceIds[]") String[] deviceIds) {
		deviceService.addToManagement(deviceIds, groupId, configId);
	}
	
	@RequestMapping(value = "/locations/{mapType}/{id}", method = RequestMethod.GET)
	public List<List<String>> getAllLocation(@PathVariable("id") String id,
			@PathVariable("mapType") String mapType) {
		if (mapType.equals("group")) {
			return locationService.getAllLocationByGroup(groupService.getGroup(id));
		} else {
			return locationService.getDeviceLocationById(deviceService.getDevice(id));
		}
	}
}
