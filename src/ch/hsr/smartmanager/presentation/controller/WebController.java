package ch.hsr.smartmanager.presentation.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.ResourceModel;
import org.eclipse.leshan.server.model.StandardModelProvider;
import org.eclipse.leshan.server.registration.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.DeviceComponent;
import ch.hsr.smartmanager.data.DeviceGroup;
import ch.hsr.smartmanager.data.ResourceModelAdapter;
import ch.hsr.smartmanager.service.ConfigurationService;
import ch.hsr.smartmanager.service.DeviceService;
import ch.hsr.smartmanager.service.ManagementUserService;
import ch.hsr.smartmanager.service.lwm2m.LwM2MHandler;
import ch.hsr.smartmanager.service.lwm2m.LwM2MManagementServer;

@Controller
public class WebController {

	@Autowired
	private DeviceService deviceService;
	@Autowired
	private ConfigurationService configService;
	@Autowired
	private ManagementUserService mangementUserService;
	@Autowired
	private LwM2MManagementServer lwM2MManagementServer;
	@Autowired
	LwM2MHandler lwM2MHandler;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showIndex(Model model, Principal principal) {
		model.addAttribute("username", principal.getName());
		model.addAttribute("discoveredDeviceCounter", deviceService.countDiscoveredDevices());
		model.addAttribute("deviceCounter", deviceService.countAllDevices());
		model.addAttribute("unreachableDevices", deviceService.getUnreachableDevices());
		model.addAttribute("registeredUsers", mangementUserService.findAll().size());
		model.addAttribute("uptime", deviceService.getServerUptime());
		return "index";
	}

	@RequestMapping(value = "/discovery")
	public String showDiscovery(Model model, Principal principal) {
		model.addAttribute("groups", deviceService.getAllGroups());
		model.addAttribute("devices", deviceService.getAllDiscoveredDevice());
		model.addAttribute("configurations", configService.getAllConfigurations());
		model.addAttribute("username", principal.getName());
		model.addAttribute("discoveredDeviceCounter", deviceService.countDiscoveredDevices());
		return "discovery";
	}

	@RequestMapping(value = "/discovery/clean")
	public String cleanDiscovery(Model model) {
		deviceService.removeDiscoveredDevices();
		return "redirect:/discovery";

	}

	@RequestMapping(value = "/configurations")
	public String showConfigurations(Model model, Principal principal) {
		model.addAttribute("username", principal.getName());
		model.addAttribute("configurations", configService.getAllConfigurations());
		model.addAttribute("discoveredDeviceCounter", deviceService.countDiscoveredDevices());
		return "configurations";
	}

	@RequestMapping(value = "/devices")
	public String showDevices(Model model, Principal principal) {
		model.addAttribute("username", principal.getName());
		model.addAttribute("discoveredDeviceCounter", deviceService.countDiscoveredDevices());
		return "devices";
	}

	@RequestMapping(value = "/devices/{id}", method = RequestMethod.GET)
	public String showDeviceDetails(Model model, @PathVariable("id") String id) {

		LinkedHashMap<String, ArrayList<ResourceModelAdapter>> objectModelList = new LinkedHashMap<String, ArrayList<ResourceModelAdapter>>();
		ArrayList<ResourceModelAdapter> resourceModelList = new ArrayList<ResourceModelAdapter>();

		final String regex = "\\/([0-9]*)\\/";
		final Pattern pattern = Pattern.compile(regex);
		Matcher matcher;

		Device dev = deviceService.getDevice(id);
		Registration registration = lwM2MManagementServer.getServer().getRegistrationService().getById(dev.getRegId());

		LwM2mModel regModel;

		if (registration == null) {
			regModel = new StandardModelProvider().getObjectModel(registration);
		} else {
			regModel = lwM2MManagementServer.getServer().getModelProvider().getObjectModel(registration);
		}

		for (String objId : dev.getObjectLinks()) {
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

		model.addAttribute("modelDescription", objectModelList);
		model.addAttribute("objectLinksDiv", dev.getObjectLinksDiv().toArray());
		model.addAttribute("objectLinks", dev.getObjectLinks().toArray());
		model.addAttribute("registration", registration);
		model.addAttribute("device", dev);

		return "deviceFragment";

	}

	@RequestMapping(value = "/groups/{id}", method = RequestMethod.GET)
	public String showGroupDetails(Model model, @PathVariable("id") String id) {
		DeviceGroup group = deviceService.getGroup(id);
		model.addAttribute("group", group);
		return "groupFragment";
	}

	@RequestMapping(value = "/groups/writeCommandToChildsFragment", method = RequestMethod.GET)
	public String writeCommandToChildsFragment(Model model) {
		model.addAttribute("objectMap", deviceService.allWritableObjectIDs());
		return "writeCommandToChildsFragment";
	}

	@RequestMapping(value = "/groups/{id}/writeConfiguration", method = RequestMethod.GET)
	public String writeConfiguration(Model model, @PathVariable("id") String id,
			@RequestParam("value") String configurationId) {
		model.addAttribute("result", configService.writeConfigurationToGroup(id, configurationId));
		return "writeConfigResultFragment";
	}

	@RequestMapping(value = "/groups/executeCommandToChildsFragment", method = RequestMethod.GET)
	public String executeCommandToChildsFragment(Model model) {
		model.addAttribute("objectMap", deviceService.allExecutableObjectIDs());
		return "executeCommandToChildsFragment";
	}

	@RequestMapping(value = "/groups/writeConfigToChildsFragment", method = RequestMethod.GET)
	public String writeConfigToChildsFragment(Model model) {
		model.addAttribute("configurations", configService.getAllConfigurations());
		return "writeConfigToChildsFragment";
	}

	@RequestMapping(value = "/configurations/createConfigurationFragment", method = RequestMethod.GET)
	public String showConfigurationFragment(Model model) {
		model.addAttribute("objectMap", deviceService.allWritableObjectIDs());
		return "createConfigurationFragment";
	}

	@RequestMapping(value = "/configurations/{id}/editConfigurationFragment", method = RequestMethod.GET)
	public String editConfigurationFragment(Model model, @PathVariable("id") String id) {
		model.addAttribute("objectMap", deviceService.allWritableObjectIDs());
		model.addAttribute("configuration", configService.findOne(id));
		return "editConfigurationFragment";
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
		if (groupMembership.size() != 0) {
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
}