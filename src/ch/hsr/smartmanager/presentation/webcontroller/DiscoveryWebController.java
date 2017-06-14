package ch.hsr.smartmanager.presentation.webcontroller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.hsr.smartmanager.service.applicationservices.ConfigurationService;
import ch.hsr.smartmanager.service.applicationservices.DeviceService;
import ch.hsr.smartmanager.service.applicationservices.GroupService;

@Controller
@RequestMapping("/discovery")
public class DiscoveryWebController {

	@Autowired
	private DeviceService deviceService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private ConfigurationService configurationService;

    @RequestMapping(method = RequestMethod.GET)
	public String showDiscovery(Model model, Principal principal) {
    	if(principal == null) return "redirect:/logout";
		model.addAttribute("groups", groupService.getAllGroups());
		model.addAttribute("devices", deviceService.getAllDiscoveredDevice());
		model.addAttribute("configurations", configurationService.getAllConfigurations());
		model.addAttribute("username", principal.getName());
		model.addAttribute("discoveredDeviceCounter", deviceService.countDiscoveredDevices());
		return "discovery/discovery";
	}

	@RequestMapping(value = "/clean", method=RequestMethod.GET)
	public String cleanDiscovery() {
		deviceService.removeDiscoveredDevices();
		return "redirect:/discovery";

	}

}
