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
import ch.hsr.smartmanager.service.DeviceService;
import ch.hsr.smartmanager.service.lwm2m.LwM2MHandler;

@Controller
@RequestMapping("/groups")
public class GroupWebController {
	
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private LwM2MHandler lwM2MHandler;
	@Autowired
	private ConfigurationService configurationService;
	
	@RequestMapping(value = "/{id}/executeChildDevices/{objectId}/{objectInstanceId}/{resourceId}", method = RequestMethod.GET)
	public String executeChildDevices(Model model, @PathVariable("id") String id, @PathVariable("objectId") int objectId,
			@PathVariable("objectInstanceId") int objectInstanceId, @PathVariable("resourceId") int resourceId) {
		
		model.addAttribute("result", lwM2MHandler.executeToAllChildren(id, objectId, objectInstanceId, resourceId));
		return "writeConfigResultFragment";
	}

	@RequestMapping(value = "/executeCommandToChildsFragment", method = RequestMethod.GET)
	public String executeCommandToChildsFragment(Model model) {
		model.addAttribute("objectMap", deviceService.allExecutableObjectIDs());
		return "executeCommandToChildsFragment";
	}

	@RequestMapping(value = "/writeConfigToChildsFragment", method = RequestMethod.GET)
	public String writeConfigToChildsFragment(Model model) {
		model.addAttribute("configurations", configurationService.getAllConfigurations());
		return "writeConfigToChildsFragment";
	}

	

	@RequestMapping(value = "/{id}/memberships", method = RequestMethod.GET)
	public String getGroupMembership(Model model, @PathVariable("id") String id) {
		List<DeviceGroup> allGroups = deviceService.getAllGroups();
		List<DeviceGroup> groupMembership = deviceService.listAllGroupsForComponents(id);
		if (groupMembership.size() != 0) {
			allGroups.remove(groupMembership);
		}

		model.addAttribute("componentName", deviceService.getGroup(id).getName());
		model.addAttribute("allGroups", allGroups);
		model.addAttribute("deviceGroups", groupMembership);

		return "groupMembershipsFragment";
	}

	@RequestMapping(value = "/{id}/members", method = RequestMethod.GET)
	public String getGroupMembers(Model model, @PathVariable("id") String id) {
		List<DeviceComponent> allComponents = deviceService.getAllComponents();
		List<DeviceComponent> groupMembers = deviceService.getGroup(id).getChildren();
		allComponents.removeAll(groupMembers);

		model.addAttribute("groupName", deviceService.getGroup(id).getName());
		model.addAttribute("allComponents", allComponents);
		model.addAttribute("groupMembers", groupMembers);

		return "groupMembersFragment";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String showGroupDetails(Model model, @PathVariable("id") String id) {
		DeviceGroup group = deviceService.getGroup(id);
		model.addAttribute("group", group);
		return "groupFragment";
	}

	@RequestMapping(value = "/writeCommandToChildsFragment", method = RequestMethod.GET)
	public String writeCommandToChildsFragment(Model model) {
		model.addAttribute("objectMap", deviceService.allWritableObjectIDs());
		return "writeCommandToChildsFragment";
	}

	@RequestMapping(value = "/{id}/writeConfiguration", method = RequestMethod.GET)
	public String writeConfiguration(Model model, @PathVariable("id") String id,
			@RequestParam("value") String configurationId) {
		model.addAttribute("result", configurationService.writeConfigurationToGroup(id, configurationId));
		return "writeConfigResultFragment";
	}
	
	@RequestMapping(value = "/{id}/writeChildDevices/{objectId}/{objectInstanceId}/{resourceId}", method = RequestMethod.GET)
	public String writeChildDevices(Model model, @PathVariable("id") String id,
			@PathVariable("objectId") int objectId, @PathVariable("objectInstanceId") int objectInstanceId,
			@PathVariable("resourceId") int resourceId, @RequestParam("value") String value) {

		model.addAttribute("result", lwM2MHandler.writeToAllChildren(id, objectId, objectInstanceId, resourceId, HtmlUtils.htmlEscape(value)));
		return "writeConfigResultFragment";
	}
	
}