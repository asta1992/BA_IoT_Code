package ch.hsr.smartmanager.presentation.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.hsr.smartmanager.data.ManagementUser;
import ch.hsr.smartmanager.service.DeviceService;
import ch.hsr.smartmanager.service.ManagementUserService;

@Controller
@RequestMapping("/users")
public class UserWebController {
	
	@Autowired
	DeviceService deviceService;
	
	@Autowired
	ManagementUserService managementUserService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showUsers(Model model, Principal principal) {
		if(principal == null) return "redirect:/logout";
		ManagementUser user = managementUserService.findUserByName(principal.getName());
		model.addAttribute("username", user.getUsername());
		model.addAttribute("discoveredDeviceCounter", deviceService.countDiscoveredDevices());
		model.addAttribute("user", user);
		model.addAttribute("managementUsers", managementUserService.findAll());
		return "userSettings";
	}
	
	@RequestMapping("/userDeleteFragment")
	public String userDeleteFragment(Model model) {
		model.addAttribute("userList", managementUserService.findAll());
		return "userDeleteFragment";
	}
	
	@RequestMapping(value="/userAddFragment")
	public String userAddFragment(Model model) {
		return "userAddFragment";
	}
	
	
	@RequestMapping("/userEditFragment")
	public String userEditFragment(Model model) {
		return "userEditFragment";
	}

}
