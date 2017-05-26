package ch.hsr.smartmanager.presentation.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ch.hsr.smartmanager.data.ManagementUser;
import ch.hsr.smartmanager.service.ManagementUserService;

@Controller
public class LoginWebController {
	
	@Autowired
	ManagementUserService managementUserService;
	
	@RequestMapping("/users")
	public String showUsers(Model model, Principal principal) {
		if(principal == null) return doLogout();
		ManagementUser user = managementUserService.findUserByName(principal.getName());
		model.addAttribute("username", user.getUsername());
		model.addAttribute("user", user);
		return "userSettings";
	}
	
	@RequestMapping("/login")
	public String showLogin() {
		return "login";
	}
	
	@RequestMapping("/logout")
	public String doLogout() {
		return "redirect:login?logout=true";
	}
	
	@RequestMapping("/users/userDeleteFragment")
	public String userDeleteFragment(Model model) {
		model.addAttribute("userList", managementUserService.findAll());
		return "userDeleteFragment";
	}
	
	
	@RequestMapping("/users/userAddFragment")
	public String userAddFragment(Model model) {
		return "userAddFragment";
	}
	
	@RequestMapping("/users/userEditFragment")
	public String userEditFragment(Model model) {
		return "userEditFragment";
	}
	
	
	
	
	
	


}
