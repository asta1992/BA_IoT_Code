package ch.hsr.smartmanager.presentation.controller;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mongodb.util.JSON;

import com.eclipsesource.json.Json;

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.DeviceComponent;
import ch.hsr.smartmanager.data.DeviceGroup;
import ch.hsr.smartmanager.service.DeviceService;
import ch.hsr.smartmanager.service.lwm2m.LwM2MHandler;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@Autowired
	LwM2MHandler lwM2MHandler;

	@Autowired
	DeviceService deviceService;

	@RequestMapping(value = "/devices/{id}/read/{objectId}/{objectInstanceId}/{resourceId}", method = RequestMethod.GET)
	public ReadResponse readResource(Model model, @PathVariable("id") String id, @PathVariable("objectId") int objectId,
			@PathVariable("objectInstanceId") int objectInstanceId, @PathVariable("resourceId") int resourceId) {

		return lwM2MHandler.read(id, objectId, objectInstanceId, resourceId);
	}

	@RequestMapping(value = "/devices/{id}/read/{objectId}", method = RequestMethod.GET)
	public ReadResponse readObject(Model model, @PathVariable("id") String id, @PathVariable("objectId") int objectId) {

		return lwM2MHandler.read(id, objectId);
	}

	@RequestMapping(value = "/devices/{id}/write/{objectId}/{objectInstanceId}/{resourceId}", method = RequestMethod.POST)
	public WriteResponse write(Model model, @PathVariable("id") String id, @PathVariable("objectId") int objectId,
			@PathVariable("objectInstanceId") int objectInstanceId, @PathVariable("resourceId") int resourceId,
			@RequestParam("value") String value) {

		return lwM2MHandler.write(id, objectId, objectInstanceId, resourceId, value);
	}

	@RequestMapping(value = "/devices/{id}/execute/{objectId}/{objectInstanceId}/{resourceId}", method = RequestMethod.GET)
	public ExecuteResponse execute(Model model, @PathVariable("id") String id, @PathVariable("objectId") int objectId,
			@PathVariable("objectInstanceId") int objectInstanceId, @PathVariable("resourceId") int resourceId) {

		return lwM2MHandler.execute(id, objectId, objectInstanceId, resourceId);
	}
	
	@RequestMapping(value = "/groups/{id}/add")
	public void addNewChildGroup(Model model,@PathVariable("id") String id,@RequestParam("value") String groupName){
		DeviceGroup devGroup = new DeviceGroup(Json.parse(groupName).asString());
		deviceService.insertGroup(devGroup);
		devGroup = deviceService.findByName(Json.parse(groupName).asString());
		deviceService.addGroupToGroup(id, devGroup.getId());
	}
	
	@RequestMapping(value = "/groups/add", method = RequestMethod.POST)
	public void addNewRootGroup(Model mode,@RequestParam("value") String groupName){
		deviceService.insertGroup(new DeviceGroup(Json.parse(groupName).asString()));
	}

	@RequestMapping(value = "/countDiscoveredDevices", method = RequestMethod.GET)
	public int countDevices(Model model) {

		return deviceService.countDiscoveredDevices();
	}

	@RequestMapping(value = "/devices/{id}/changeMembership", method = RequestMethod.POST)
	public void addToGroups(Model model, @PathVariable("id") String id, @RequestParam("value") JSONArray value) {

		List<DeviceGroup> postGroups = new ArrayList<>();
		for (int i = 0; i < value.length(); i++) {
			try {
				postGroups.add(deviceService.getGroup(value.getString(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		Device device = deviceService.getDevice(id);
		List<DeviceGroup> preGroups = deviceService.listAllGroupsForDevice(id);

		for (DeviceGroup devGroups : preGroups) {
			if (!postGroups.contains(devGroups)) {
				deviceService.removeDeviceFromGroup(devGroups.getId(), device.getId());
			}
		}
		for (DeviceGroup devGroups : postGroups) {
			if (!preGroups.contains(devGroups)) {
				deviceService.addDeviceToGroup(devGroups.getId(), device.getId());
			}
		}
		
		DeviceGroup devGroup = deviceService.findByName("_unassigned");
		
		
		if(postGroups.size() > 1 && postGroups.contains(devGroup)) {
			deviceService.removeDeviceFromGroup(devGroup.getId(), id);
		}
		if (postGroups.isEmpty()) {
			deviceService.addDeviceToGroup(devGroup.getId(), id);
		}

	}

	@RequestMapping(value = "/devices/{id}/removeFromGroups", method = RequestMethod.POST)
	public void removeFromGroups(Model model, @PathVariable("id") String id,
			@RequestParam("value") List<String> value) {
		for (String groupId : value) {
			deviceService.removeDeviceFromGroup(groupId, id);
		}
	}

	@RequestMapping(value = "/group/list", method = RequestMethod.GET)
	public List<DeviceGroup> getGroupList(Model model) {
		return deviceService.getAllGroups();
	}

	@RequestMapping(value = "/group/getAll", method = RequestMethod.GET)
	public String getAllGroups(Model model) throws JSONException {
		List<JSONObject> allJson = new ArrayList<>();

		for (DeviceComponent item : deviceService.getAllGroups()) {
			if (!deviceService.isRoot(item.getId())) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", "groups/" + item.getId());
				jsonObj.put("text", item.getName());
				jsonObj.put("children", allChildren(item));
				allJson.add(jsonObj);
			}
		}

		return allJson.toString();
	}

	private List<JSONObject> allChildren(DeviceComponent deviceComponent) throws JSONException {
		List<JSONObject> jsonObjects = new ArrayList<>();
		for (DeviceComponent item : deviceComponent.getChildren()) {
			JSONObject jsonObj = new JSONObject();
			if (item instanceof Device) {
				jsonObj.put("id", "devices/" + item.getId());
				jsonObj.put("text", item.getName());
				jsonObjects.add(jsonObj);
			} else {
				item = deviceService.getGroup(item.getId());

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
}
