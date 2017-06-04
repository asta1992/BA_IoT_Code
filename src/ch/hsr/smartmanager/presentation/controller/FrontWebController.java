package ch.hsr.smartmanager.presentation.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ch.hsr.smartmanager.service.DeviceService;
import ch.hsr.smartmanager.service.ManagementUserService;

@Controller
@RequestMapping("/")
public class FrontWebController {

	@Autowired
	private DeviceService deviceService;
	@Autowired
	private ManagementUserService mangementUserService;

    @RequestMapping(method = RequestMethod.GET)
	public String showIndex(Model model, Principal principal) {
		model.addAttribute("username", principal.getName());
		model.addAttribute("discoveredDeviceCounter", deviceService.countDiscoveredDevices());
		model.addAttribute("deviceCounter", deviceService.countAllDevices());
		model.addAttribute("unreachableDevices", deviceService.getUnreachableDevices());
		model.addAttribute("registeredUsers", mangementUserService.findAll().size());
		model.addAttribute("uptime", deviceService.getServerUptime());
		return "index";
	}
    
	@RequestMapping("/login")
	public String showLogin() {
		return "login";
	}
	
	@RequestMapping("/logout")
	public String doLogout() {
		return "redirect:login?logout=true";
	}

}