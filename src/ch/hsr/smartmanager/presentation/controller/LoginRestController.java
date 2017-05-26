package ch.hsr.smartmanager.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.hsr.smartmanager.service.ManagementUserService;

@RestController
public class LoginRestController {

	@Autowired
	private ManagementUserService managementUserService;

	@RequestMapping(value = "/users/delete", method = RequestMethod.POST)
	public String deleteUser(Model model, @RequestParam("username") String username) {
		boolean result = managementUserService.deleteUser(username);
		return Boolean.toString(result);
	}

	@RequestMapping(value = "users/{id}/edit", method = RequestMethod.POST)
	public String editUser(Model model, @PathVariable("id") String id, @RequestParam("oldPassword") String oldPassword,
			@RequestParam("firstPassword") String firstPassword,
			@RequestParam("secondPassword") String secondPassword) {

		if (!managementUserService.checkOldPassword(id, oldPassword))
			return Boolean.toString(false);
		return Boolean.toString(managementUserService.updateUser(id, firstPassword, secondPassword));
	}
	
	@RequestMapping(value = "/users/add", method = RequestMethod.POST)
	public String addUser(Model model, @RequestParam("username") String username,
			@RequestParam("firstPassword") String firstPassword,
			@RequestParam("secondPassword") String secondPassword) {
		boolean result = managementUserService.addUser(username, firstPassword, secondPassword);
		return Boolean.toString(result);
	}
	
	@RequestMapping(value = "/users/checkUser", method = RequestMethod.POST)
	public String checkUser(Model model, @RequestParam("username") String username) {
		boolean result = managementUserService.checkUser(username);
		return Boolean.toString(result);
	}
	

	
}
