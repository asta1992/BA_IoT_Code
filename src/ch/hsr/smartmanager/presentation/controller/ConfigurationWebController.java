package ch.hsr.smartmanager.presentation.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.hsr.smartmanager.service.ConfigurationService;
import ch.hsr.smartmanager.service.DeviceService;
import ch.hsr.smartmanager.service.LwMwMService;

@Controller
@RequestMapping("/configurations")
public class ConfigurationWebController {
	
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private LwMwMService lwMwMService;
	

	@RequestMapping(method = RequestMethod.GET)
	public String showConfigurations(Model model, Principal principal) {
		model.addAttribute("username", principal.getName());
		model.addAttribute("configurations", configurationService.getAllConfigurations());
		model.addAttribute("discoveredDeviceCounter", deviceService.countDiscoveredDevices());
		return "configurations/configurations";
	}
	
	@RequestMapping(value = "/createConfigurationFragment", method = RequestMethod.GET)
	public String showConfigurationFragment(Model model) {
		model.addAttribute("objectMap", lwMwMService.allWritableObjectIDs());
		return "configurations/createConfigurationFragment";
	}

	@RequestMapping(value = "/{id}/editConfigurationFragment", method = RequestMethod.GET)
	public String editConfigurationFragment(Model model, @PathVariable("id") String id) {
		model.addAttribute("objectMap", lwMwMService.allWritableObjectIDs());
		model.addAttribute("configuration", configurationService.findOne(id));
		return "configurations/editConfigurationFragment";
	}



}
