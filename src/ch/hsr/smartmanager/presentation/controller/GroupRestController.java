package ch.hsr.smartmanager.presentation.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.hsr.smartmanager.data.DeviceGroup;
import ch.hsr.smartmanager.data.ResourceModelAdapter;
import ch.hsr.smartmanager.service.GroupService;
import ch.hsr.smartmanager.service.LwMwMService;

@RestController
@RequestMapping("/groups")
public class GroupRestController {

	@Autowired
	private GroupService groupService;
	@Autowired
	private LwMwMService lwMwMService;

	@RequestMapping(value = "/{id}/add")
	public String addNewChildGroup(@PathVariable("id") String id, @RequestParam("value") String groupName) {
		return Boolean.toString(groupService.addNewChildGroup(id, groupName));
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addNewRootGroup(@RequestParam("value") String groupName) {
		return Boolean.toString(groupService.addNewRootGroup(groupName));
	}

	@RequestMapping(value = "/{id}/changeMembership", method = RequestMethod.POST)
	public void addGroupToGroup(@PathVariable("id") String id, @RequestParam("value") JSONArray value) {
		groupService.changeMembership(id, value);
	}

	@RequestMapping(value = "/{id}/changeMembers", method = RequestMethod.POST)
	public void changeMembers(@PathVariable("id") String id, @RequestParam("value") JSONArray value) {
		groupService.changeMembers(id, value);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<DeviceGroup> getGroupList() {
		return groupService.getAllGroups();
	}

	@RequestMapping(value = "/{objectId}/writeToChildren", method = RequestMethod.GET)
	public List<ResourceModelAdapter> writeToChildren(@PathVariable("objectId") String objectId) {
		return lwMwMService.allWritableResources(objectId);
	}

	@RequestMapping(value = "/{objectId}/executeToChildren", method = RequestMethod.GET)
	public List<ResourceModelAdapter> executeToChildren(@PathVariable("objectId") String objectId) {
		return lwMwMService.allExecuteableResources(objectId);
	}

	@RequestMapping(value = "/{objectId}/multiInstance", method = RequestMethod.GET)
	public Map<String, Boolean> multiInstance(@PathVariable("objectId") String objectId) {
		return Collections.singletonMap("value", lwMwMService.isMultiInstance(objectId));
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public String getAllGroups() {
		return groupService.getAllGroupsAsJSON();
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
	public void removeGroup(@PathVariable("id") String id) {
		groupService.deleteGroup(id);
	}
}
