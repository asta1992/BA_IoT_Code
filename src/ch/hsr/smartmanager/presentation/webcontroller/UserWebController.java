package ch.hsr.smartmanager.presentation.webcontroller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.hsr.smartmanager.data.ManagementUser;
import ch.hsr.smartmanager.service.applicationservices.DeviceService;
import ch.hsr.smartmanager.service.applicationservices.UserService;

@Controller
@RequestMapping("/users")
public class UserWebController {
	
	@Autowired
	DeviceService deviceService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showUsers(Model model, Principal principal) {
		if(principal == null) return "redirect:/logout";
		ManagementUser user = userService.findUserByName(principal.getName());
		model.addAttribute("username", principal.getName());
		model.addAttribute("discoveredDeviceCounter", deviceService.countDiscoveredDevices());
		model.addAttribute("user", user);
		model.addAttribute("managementUsers", userService.findAll());
		return "users/userSettings";
	}
	
	@RequestMapping(value="/userDeleteFragment", method=RequestMethod.GET)
	public String userDeleteFragment(Model model) {
		model.addAttribute("userList", userService.findAll());
		return "users/userDeleteFragment";
	}
	
	@RequestMapping(value="/userAddFragment", method=RequestMethod.GET)
	public String userAddFragment(Model model) {
		return "users/userAddFragment";
	}
	
	
	@RequestMapping(value="/userEditFragment", method=RequestMethod.GET)
	public String userEditFragment(Model model) {
		return "users/userEditFragment";
	}

}
