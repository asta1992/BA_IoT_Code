package ch.hsr.smartmanager.presentation.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.DeviceComponent;
import ch.hsr.smartmanager.data.DeviceGroup;
import ch.hsr.smartmanager.data.ResourceModelAdapter;
import ch.hsr.smartmanager.service.DeviceService;
import ch.hsr.smartmanager.service.GroupService;
import ch.hsr.smartmanager.service.LwMwMService;

@RestController
@RequestMapping("/groups")
public class GroupRestController {

	@Autowired
	DeviceService deviceService;
	@Autowired
	GroupService groupService;
	@Autowired
	private LwMwMService lwMwMService;
	


	@RequestMapping(value = "/{id}/add")
	public String addNewChildGroup(Model model, @PathVariable("id") String id,
			@RequestParam("value") String groupName) {
		DeviceGroup devGroup = groupService.insertGroup(groupName);
		if (devGroup != null) {
			groupService.addGroupToGroup(id, devGroup.getId());
			return Boolean.toString(true);
		} else {
			return Boolean.toString(false);
		}

	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addNewRootGroup(Model mode, @RequestParam("value") String groupName) {
		if (groupService.insertGroup(groupName) != null) {
			return Boolean.toString(true);
		} else {
			return Boolean.toString(false);
		}
	}

	@RequestMapping(value = "/{id}/changeMembership", method = RequestMethod.POST)
	public void addGroupToGroup(Model model, @PathVariable("id") String id, @RequestParam("value") JSONArray value) {
		if (value.length() == 1) {
			try {
				DeviceGroup newParentGroup = groupService.getGroup(value.getString(0));
				DeviceGroup group = groupService.getGroup(id);

				List<DeviceGroup> oldParentGroups = groupService.listAllGroupsForGroup(id);

				if (!oldParentGroups.isEmpty()) {
					groupService.removeGroupFromGroup(oldParentGroups.get(0).getId(), group.getId());
				}
				groupService.addGroupToGroup(newParentGroup.getId(), group.getId());

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if (value.length() == 0) {
			DeviceGroup group = groupService.getGroup(id);
			List<DeviceGroup> subGroups = groupService.listAllGroupsForGroup(id);
			if (subGroups.isEmpty()) {
				return;
			}
			DeviceGroup oldParentGroup = subGroups.get(0);
			groupService.removeGroupFromGroup(oldParentGroup.getId(), group.getId());
		}
	}

	@RequestMapping(value = "/{id}/changeMembers", method = RequestMethod.POST)
	public void changeMembers(Model model, @PathVariable("id") String id, @RequestParam("value") JSONArray value) {
		Set<Device> editedDevices = new HashSet<>();

		List<DeviceComponent> postMembers = new ArrayList<>();
		for (int i = 0; i < value.length(); i++) {
			try {

				DeviceComponent item = groupService.getGroup((value.getString(i)));
				if (item == null) {
					item = deviceService.getDevice((value.getString(i)));
				}
				postMembers.add(item);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		postMembers.removeAll(Collections.singleton(null));

		DeviceGroup group = groupService.getGroup(id);
		List<DeviceComponent> preMembers = group.getChildren();

		for (DeviceComponent comp : preMembers) {
			if (!postMembers.contains(comp)) {
				if (comp instanceof Device) {
					editedDevices.add((Device) comp);
					groupService.removeDeviceFromGroup(group.getId(), comp.getId());
				}
				if (comp instanceof DeviceGroup) {
					groupService.removeGroupFromGroup(group.getId(), comp.getId());
				}
			}
		}

		for (DeviceComponent comp : postMembers) {
			if (!preMembers.contains(comp)) {
				if (comp instanceof Device) {
					editedDevices.add((Device) comp);
					groupService.addDeviceToGroup(group.getId(), comp.getId());
				}
				if (comp instanceof DeviceGroup) {
					groupService.addGroupToGroup(group.getId(), comp.getId());
				}
			}
		}

		DeviceGroup devGroup = groupService.findByName("_unassigned");

		for (Device device : editedDevices) {
			List<DeviceGroup> deviceGroups = groupService.listAllGroupsForGroup(device.getId());
			if (deviceGroups.isEmpty()) {
				groupService.addDeviceToGroup(devGroup.getId(), device.getId());
			} else {
				groupService.removeDeviceFromGroup(devGroup.getId(), device.getId());
			}
		}

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<DeviceGroup> getGroupList(Model model) {
		return groupService.getAllGroups();
	}

	@RequestMapping(value = "/{objectId}/writeToChildren", method = RequestMethod.GET)
	public List<ResourceModelAdapter> writeToChildren(Model model, @PathVariable("objectId") String objectId) {
		return lwMwMService.allWritableResources(objectId);
	}

	@RequestMapping(value = "/{objectId}/executeToChildren", method = RequestMethod.GET)
	public List<ResourceModelAdapter> executeToChildren(Model model, @PathVariable("objectId") String objectId) {
		return lwMwMService.allExecuteableResources(objectId);
	}

	@RequestMapping(value = "/{objectId}/multiInstance", method = RequestMethod.GET)
	public Map<String, Boolean> multiInstance(Model model, @PathVariable("objectId") String objectId) {
		return Collections.singletonMap("value", lwMwMService.isMultiInstance(objectId));
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public String getAllGroups(Model model) throws JSONException {
		List<JSONObject> allJson = new ArrayList<>();

		for (DeviceComponent item : groupService.getAllGroups()) {
			if (!groupService.isRoot(item.getId())) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", "groups/" + item.getId());
				jsonObj.put("text", item.getName());
				jsonObj.put("children", deviceService.allChildren(item));
				allJson.add(jsonObj);
			}
		}

		return allJson.toString();
	}
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
	public void removeGroup(Model model, @PathVariable("id") String id) {
		groupService.deleteGroup(id);
	}
}
