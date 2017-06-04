package ch.hsr.smartmanager.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import ch.hsr.smartmanager.data.DeviceComponent;
import ch.hsr.smartmanager.data.DeviceGroup;
import ch.hsr.smartmanager.service.ConfigurationService;
import ch.hsr.smartmanager.service.GroupService;
import ch.hsr.smartmanager.service.LwMwMService;
import ch.hsr.smartmanager.service.lwm2m.LwM2MHandler;

@Controller
@RequestMapping("/groups")
public class GroupWebController {
	
	@Autowired
	private GroupService groupService;
	@Autowired
	private LwM2MHandler lwM2MHandler;
	@Autowired
	private LwMwMService lwMwMService;
	@Autowired
	private ConfigurationService configurationService;
	
	@RequestMapping(value = "/{id}/executeChildDevices/{objectId}/{objectInstanceId}/{resourceId}", method = RequestMethod.GET)
	public String executeChildDevices(Model model, @PathVariable("id") String id, @PathVariable("objectId") int objectId,
			@PathVariable("objectInstanceId") int objectInstanceId, @PathVariable("resourceId") int resourceId) {
		
		model.addAttribute("result", lwM2MHandler.executeToAllChildren(id, objectId, objectInstanceId, resourceId));
		return "devices/writeConfigResultFragment";
	}

	@RequestMapping(value = "/executeCommandToChildsFragment", method = RequestMethod.GET)
	public String executeCommandToChildsFragment(Model model) {
		model.addAttribute("objectMap", lwMwMService.allExecutableObjectIDs());
		return "devices/executeCommandToChildsFragment";
	}

	@RequestMapping(value = "/writeConfigToChildsFragment", method = RequestMethod.GET)
	public String writeConfigToChildsFragment(Model model) {
		model.addAttribute("configurations", configurationService.getAllConfigurations());
		return "devices/writeConfigToChildsFragment";
	}

	

	@RequestMapping(value = "/{id}/memberships", method = RequestMethod.GET)
	public String getGroupMembership(Model model, @PathVariable("id") String id) {
		List<DeviceGroup> allGroups = groupService.getAllGroups();
		List<DeviceGroup> groupMembership = groupService.listAllGroupsForGroup(id);
		if (groupMembership.size() != 0) {
			allGroups.remove(groupMembership);
		}

		model.addAttribute("componentName", groupService.getGroup(id).getName());
		model.addAttribute("allGroups", allGroups);
		model.addAttribute("deviceGroups", groupMembership);

		return "devices/groupMembershipsFragment";
	}

	@RequestMapping(value = "/{id}/members", method = RequestMethod.GET)
	public String getGroupMembers(Model model, @PathVariable("id") String id) {
		List<DeviceComponent> allComponents = groupService.getAllComponents();
		List<DeviceComponent> groupMembers = groupService.getGroup(id).getChildren();
		allComponents.removeAll(groupMembers);

		model.addAttribute("groupName", groupService.getGroup(id).getName());
		model.addAttribute("allComponents", allComponents);
		model.addAttribute("groupMembers", groupMembers);

		return "devices/groupMembersFragment";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String showGroupDetails(Model model, @PathVariable("id") String id) {
		DeviceGroup group = groupService.getGroup(id);
		model.addAttribute("group", group);
		return "devices/groupFragment";
	}

	@RequestMapping(value = "/writeCommandToChildsFragment", method = RequestMethod.GET)
	public String writeCommandToChildsFragment(Model model) {
		model.addAttribute("objectMap", lwMwMService.allWritableObjectIDs());
		return "devices/writeCommandToChildsFragment";
	}

	@RequestMapping(value = "/{id}/writeConfiguration", method = RequestMethod.GET)
	public String writeConfiguration(Model model, @PathVariable("id") String id,
			@RequestParam("value") String configurationId) {
		model.addAttribute("result", configurationService.writeConfigurationToGroup(id, configurationId));
		return "devices/writeConfigResultFragment";
	}
	
	@RequestMapping(value = "/{id}/writeChildDevices/{objectId}/{objectInstanceId}/{resourceId}", method = RequestMethod.GET)
	public String writeChildDevices(Model model, @PathVariable("id") String id,
			@PathVariable("objectId") int objectId, @PathVariable("objectInstanceId") int objectInstanceId,
			@PathVariable("resourceId") int resourceId, @RequestParam("value") String value) {

		model.addAttribute("result", lwM2MHandler.writeToAllChildren(id, objectId, objectInstanceId, resourceId, HtmlUtils.htmlEscape(value)));
		return "devices/writeConfigResultFragment";
	}
	
}
