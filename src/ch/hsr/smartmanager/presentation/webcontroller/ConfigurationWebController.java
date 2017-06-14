package ch.hsr.smartmanager.presentation.webcontroller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.hsr.smartmanager.service.applicationservices.ConfigurationService;
import ch.hsr.smartmanager.service.applicationservices.DeviceService;
import ch.hsr.smartmanager.service.lwm2mservices.LwM2MHandler;

@Controller
@RequestMapping("/configurations")
public class ConfigurationWebController {
	
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private LwM2MHandler lwM2MHandler;
	

	@RequestMapping(method = RequestMethod.GET)
	public String showConfigurations(Model model, Principal principal) {
		if(principal == null) return "redirect:/logout";
		model.addAttribute("username", principal.getName());
		model.addAttribute("configurations", configurationService.getAllConfigurations());
		model.addAttribute("discoveredDeviceCounter", deviceService.countDiscoveredDevices());
		return "configurations/configurations";
	}
	
	@RequestMapping(value = "/createConfigurationFragment", method = RequestMethod.GET)
	public String showConfigurationFragment(Model model) {
		model.addAttribute("objectMap", lwM2MHandler.allWritableObjectIDs());
		return "configurations/createConfigurationFragment";
	}

	@RequestMapping(value = "/{id}/editConfigurationFragment", method = RequestMethod.GET)
	public String editConfigurationFragment(Model model, @PathVariable("id") String id) {
		model.addAttribute("objectMap", lwM2MHandler.allWritableObjectIDs());
		model.addAttribute("configuration", configurationService.findOne(id));
		return "configurations/editConfigurationFragment";
	}



}
