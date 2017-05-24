package ch.hsr.smartmanager.presentation.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.ResourceModel;
import org.eclipse.leshan.server.registration.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.DeviceComponent;
import ch.hsr.smartmanager.data.DeviceGroup;
import ch.hsr.smartmanager.data.ResourceModelAdapter;
import ch.hsr.smartmanager.service.ConfigurationService;
import ch.hsr.smartmanager.service.DeviceService;
import ch.hsr.smartmanager.service.lwm2m.LwM2MHandler;
import ch.hsr.smartmanager.service.lwm2m.LwM2MManagementServer;

@Controller
public class WebController {

	@Autowired
	private DeviceService deviceService;
	@Autowired
	private ConfigurationService configService;
	@Autowired
	private LwM2MManagementServer lwM2MManagementServer;
	@Autowired
	LwM2MHandler lwM2MHandler;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showIndex(Model model) {
		System.out.println(deviceService.allWritableObjectIDs());
		return "index";
	}

	@RequestMapping(value = "/discovery")
	public String showDiscovery(Model model) {
		model.addAttribute("groups", deviceService.getAllGroups());
		model.addAttribute("devices", deviceService.getAllDiscoveredDevice());
		model.addAttribute("configurations", configService.getAllConfigurations());
		return "discovery";
	}
	
	@RequestMapping(value = "/configurations")
	public String showConfigurations(Model model) {
		model.addAttribute("configurations", configService.getAllConfigurations());
		return "configurations";
	}
	
	@RequestMapping(value = "/devices")
	public String showDevices(Model model) {
		return "devices";
	}
	
	@RequestMapping(value = "/devices/{id}", method = RequestMethod.GET)
	public String showDeviceDetails(Model model, @PathVariable("id") String id) {

		Device dev = deviceService.getDevice(id);
		Registration reg = lwM2MManagementServer.getServer().getRegistrationService().getById(dev.getRegId());

		LwM2mModel regModel = lwM2MManagementServer.getServer().getModelProvider().getObjectModel(reg);

		LinkedHashMap<String, ArrayList<ResourceModelAdapter>> objectModelList = new LinkedHashMap<String, ArrayList<ResourceModelAdapter>>();
		ArrayList<ResourceModelAdapter> resourceModelList = new ArrayList<ResourceModelAdapter>();

		for (int objId : dev.getObjectLinks()) {
			ObjectModel objectModel = regModel.getObjectModel(objId);
			resourceModelList = new ArrayList<ResourceModelAdapter>();

			for (ResourceModel entry : objectModel.resources.values()) {
				resourceModelList.add(new ResourceModelAdapter(entry));
			}
			objectModelList.put(objectModel.name, resourceModelList);
		}
		
		model.addAttribute("modelDescription", objectModelList);
		model.addAttribute("objectLinks", dev.getObjectLinks().toArray());
		model.addAttribute("registration", reg);
		model.addAttribute("device", dev);
		
		return "deviceFragment";
	}
	
	@RequestMapping(value = "/groups/{id}", method = RequestMethod.GET)
	public String showGroupDetails(Model model, @PathVariable("id") String id) {
		DeviceGroup group = deviceService.getGroup(id);
		model.addAttribute("group", group);
		return "groupFragment";
	}
	
	@RequestMapping(value = "/groups/writeToChildsFragment", method = RequestMethod.GET)
	public String showGroupDetails(Model model) {
		model.addAttribute("objectMap", deviceService.allWritableObjectIDs());
		return "writeToChildsFragment";
	}
	
	@RequestMapping(value = "/configurations/createConfigurationFragment", method = RequestMethod.GET)
	public String showConfigurationFragment(Model model) {
		model.addAttribute("objectMap", deviceService.allWritableObjectIDs());
		return "createConfigurationFragment";
	}
	
	@RequestMapping(value = "/devices/{id}/memberships", method = RequestMethod.GET)
	public String getDeviceMemberships(Model model, @PathVariable("id") String id) {
		List<DeviceGroup> groups = deviceService.getAllGroups();
		List<DeviceGroup> deviceGroups = deviceService.listAllGroupsForComponents(id);
		
		groups.removeAll(deviceGroups);
		
		model.addAttribute("componentName", deviceService.getDevice(id).getName());
		model.addAttribute("allGroups", groups);
		model.addAttribute("deviceGroups", deviceGroups);

		return "groupMembershipsFragment";
	}
	
	@RequestMapping(value = "/groups/{id}/memberships", method = RequestMethod.GET)
	public String getGroupMembership(Model model, @PathVariable("id") String id) {
		List<DeviceGroup> allGroups = deviceService.getAllGroups();
		List<DeviceGroup> groupMembership = deviceService.listAllGroupsForComponents(id);
		if(groupMembership.size() != 0) {
			allGroups.remove(groupMembership);
		}
		
		model.addAttribute("componentName", deviceService.getGroup(id).getName());
		model.addAttribute("allGroups", allGroups);
		model.addAttribute("deviceGroups", groupMembership);

		return "groupMembershipsFragment";
	}
	
	@RequestMapping(value = "/groups/{id}/members", method = RequestMethod.GET)
	public String getGroupMembers(Model model, @PathVariable("id") String id) {
		List<DeviceComponent> allComponents = deviceService.getAllComponents();
		List<DeviceComponent> groupMembers = deviceService.getGroup(id).getChildren();
		allComponents.removeAll(groupMembers);
		
		model.addAttribute("groupName", deviceService.getGroup(id).getName());
		model.addAttribute("allComponents", allComponents);
		model.addAttribute("groupMembers", groupMembers);

		return "groupMembersFragment";
	}
	
	@RequestMapping(value = "/devices/{id}/add", method = RequestMethod.GET)
	public String addDevice(Model model, @PathVariable("id") String id) {
		deviceService.addToManagement(id);
		return "redirect:/discovery";
	}

	@RequestMapping(value = "/devices/{id}/delete", method = RequestMethod.DELETE)
	public String removeDevice(Model model, @PathVariable("id") String id) {
		deviceService.removeFromManagement(id);
		return "redirect:/devices";
	}
	
	@RequestMapping(value = "/groups/{id}/delete", method = RequestMethod.DELETE)
	public String removeGroup(Model model, @PathVariable("id") String id) {
		deviceService.deleteGroup(id);
		return "forward:/devices";
		
	}
}