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

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.DeviceGroup;
import ch.hsr.smartmanager.data.ResourceModelAdapter;
import ch.hsr.smartmanager.service.DeviceService;
import ch.hsr.smartmanager.service.lwm2m.LwM2MManagementServer;

@Controller
@RequestMapping("/devices")
public class DeviceWebController {
	
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private LwM2MManagementServer lwM2MManagementServer;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String showDevices(Model model, Principal principal) {
		model.addAttribute("username", principal.getName());
		model.addAttribute("discoveredDeviceCounter", deviceService.countDiscoveredDevices());
		return "devices";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/{id}/memberships", method = RequestMethod.GET)
	public String getDeviceMemberships(Model model, @PathVariable("id") String id) {
		List<DeviceGroup> groups = deviceService.getAllGroups();
		List<DeviceGroup> deviceGroups = deviceService.listAllGroupsForComponents(id);

		groups.removeAll(deviceGroups);

		model.addAttribute("componentName", deviceService.getDevice(id).getName());
		model.addAttribute("allGroups", groups);
		model.addAttribute("deviceGroups", deviceGroups);

		return "groupMembershipsFragment";
	}

}
