package ch.hsr.smartmanager.presentation.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

import com.eclipsesource.json.Json;

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.DeviceComponent;
import ch.hsr.smartmanager.data.DeviceGroup;
import ch.hsr.smartmanager.data.ResourceModelAdapter;
import ch.hsr.smartmanager.service.ConfigurationService;
import ch.hsr.smartmanager.service.DeviceService;
import ch.hsr.smartmanager.service.lwm2m.LwM2MHandler;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@Autowired
	LwM2MHandler lwM2MHandler;

	@Autowired
	DeviceService deviceService;
	
	@Autowired
	ConfigurationService configService;

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
		List<DeviceGroup> preGroups = deviceService.listAllGroupsForComponents(id);

		for (DeviceGroup devGroups : preGroups) {
			if (!postGroups.contains(devGroups)) {
				System.out.println("REMOVE");
				deviceService.removeDeviceFromGroup(devGroups.getId(), device.getId());
			}
		}
		for (DeviceGroup devGroups : postGroups) {
			if (!preGroups.contains(devGroups)) {
				deviceService.addDeviceToGroup(devGroups.getId(), device.getId());
			}
		}

		DeviceGroup devGroup = deviceService.findByName("_unassigned");

		if (postGroups.size() > 1 && postGroups.contains(devGroup)) {
			deviceService.removeDeviceFromGroup(devGroup.getId(), id);
		}
		if (postGroups.isEmpty()) {
			deviceService.addDeviceToGroup(devGroup.getId(), id);
		}
	}
	
	@RequestMapping(value = "/groups/{id}/changeMembership", method = RequestMethod.POST)
	public void addGroupToGroup(Model model, @PathVariable("id") String id, @RequestParam("value") JSONArray value) {
		if(value.length() == 1) {
			try {
				DeviceGroup newParentGroup = deviceService.getGroup(value.getString(0));
				DeviceGroup oldParentGroup = deviceService.listAllGroupsForComponents(id).get(0);
				DeviceGroup group = deviceService.getGroup(id);
				
				deviceService.removeGroupFromGroup(oldParentGroup.getId(), group.getId());
				
				deviceService.addGroupToGroup(newParentGroup.getId(), group.getId());
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value = "/groups/{id}/changeMembers", method = RequestMethod.POST)
	public void changeMembers(Model model, @PathVariable("id") String id, @RequestParam("value") JSONArray value) {
		List<DeviceComponent> postMembers = new ArrayList<>();
		for (int i = 0; i < value.length(); i++) {
			try {

				DeviceComponent item = deviceService.getGroup((value.getString(i)));
				if(item == null) {
					item = deviceService.getDevice((value.getString(i)));
				}
				postMembers.add(item);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		postMembers.removeAll(Collections.singleton(null));
		
		DeviceGroup group = deviceService.getGroup(id);
		List<DeviceComponent> preMembers = group.getChildren();

		for (DeviceComponent comp : preMembers) {
			if (!postMembers.contains(comp)) {
				if(comp instanceof Device){
					deviceService.removeDeviceFromGroup(group.getId(), comp.getId());
				}
				if(comp instanceof DeviceGroup){
					deviceService.removeGroupFromGroup(group.getId(), comp.getId());
				}
			}
		}
		
		for (DeviceComponent comp : postMembers) {
			if (!preMembers.contains(comp)) {
				if(comp instanceof Device){
					deviceService.addDeviceToGroup(group.getId(), comp.getId());
				}
				if(comp instanceof DeviceGroup){
					deviceService.addGroupToGroup(group.getId(), comp.getId());
				}
			}
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
	
	@RequestMapping(value = "/group/{objectId}/writeToChildren", method = RequestMethod.GET)
	public List<ResourceModelAdapter> writeToChildren(Model model, @PathVariable("objectId") String objectId) {
		return deviceService.allWritableResources(objectId);
	}
	
	@RequestMapping(value = "/group/{objectId}/executeToChildren", method = RequestMethod.GET)
	public List<ResourceModelAdapter> executeToChildren(Model model, @PathVariable("objectId") String objectId) {
		return deviceService.allExecuteableResources(objectId);
	}
	
	@RequestMapping(value = "/group/{objectId}/multiInstance", method = RequestMethod.GET)
	public Map<String, Boolean> multiInstance(Model model, @PathVariable("objectId") String objectId) {
		return Collections.singletonMap("value", deviceService.isMultiInstance(objectId));
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
	
	@RequestMapping(value = "/groups/{id}/writeChildDevices/{objectId}/{objectInstanceId}/{resourceId}", method = RequestMethod.POST)
	public List<WriteResponse> writeChildDevices(Model model,@PathVariable("id") String id, @PathVariable("objectId") int objectId,
			@PathVariable("objectInstanceId") int objectInstanceId, @PathVariable("resourceId") int resourceId,
			@RequestParam("value") String value){
		return lwM2MHandler.writeToAllChildren(id, objectId, objectInstanceId, resourceId, value);
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
	
	@RequestMapping(value = "/configurations/add")
	public void addConfiguration(Model model,@RequestParam("value") JSONArray value){
		configService.saveConfiguration(value);
	}
	
	@RequestMapping(value = "/configurations/delete")
	public void deleteConfiguration(Model model,@RequestParam("value") String value){
		configService.deleteConfiguration(value);
	}
	
	@RequestMapping(value = "/groups/{id}/writeConfiguration", method = RequestMethod.POST)
	public Map<String, List<WriteResponse>> writeConfiguration(Model model, @PathVariable("id") String id, @RequestParam("value") String configurationId){
		return configService.writeConfigurationToGroup(id, configurationId);
	}
}
