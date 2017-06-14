package ch.hsr.smartmanager.presentation.webcontroller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.DeviceGroup;
import ch.hsr.smartmanager.service.applicationservices.DeviceService;
import ch.hsr.smartmanager.service.applicationservices.GroupService;

@Controller
@RequestMapping("/devices")
public class DeviceWebController {

	@Autowired
	private DeviceService deviceService;
	@Autowired
	private GroupService groupService;

	@RequestMapping(method = RequestMethod.GET)
	public String showDevices(Model model, Principal principal) {
		if(principal == null) return "redirect:/logout";
		model.addAttribute("username", principal.getName());
		model.addAttribute("discoveredDeviceCounter", deviceService.countDiscoveredDevices());
		return "devices/devices";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String showDeviceDetails(Model model, @PathVariable("id") String id) {
		Device device = deviceService.getDevice(id);
		
		if (device == null) {
			deviceService.removeNullDevice(id);
			return "devices/deviceError";
		} else {
			model.addAttribute("objectLinksDiv", device.getObjectLinksDiv().toArray());
			model.addAttribute("objectLinks", device.getObjectLinks().toArray());
			model.addAttribute("modelDescription", deviceService.getObjectModelList(id));
			model.addAttribute("device", device);
			return "devices/deviceFragment";
		}
	}

	@RequestMapping(value = "/{id}/memberships", method = RequestMethod.GET)
	public String getDeviceMemberships(Model model, @PathVariable("id") String id) {
		List<DeviceGroup> groups = groupService.getAllGroups();
		List<DeviceGroup> deviceGroups = groupService.listAllGroupsForGroup(id);

		groups.removeAll(deviceGroups);

		model.addAttribute("componentName", deviceService.getDevice(id).getName());
		model.addAttribute("allGroups", groups);
		model.addAttribute("deviceGroups", deviceGroups);

		return "devices/groupMembershipsFragment";
	}

}
