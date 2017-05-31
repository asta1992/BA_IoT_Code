package ch.hsr.smartmanager.presentation.controller;

import org.json.JSONException;
import org.json.JSONObject;
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
	public JSONObject editUser(Model model, @PathVariable("id") String id, @RequestParam("oldPassword") String oldPassword,
			@RequestParam("firstPassword") String firstPassword,
			@RequestParam("secondPassword") String secondPassword) {

			JSONObject result = new JSONObject();
			try {
				result = managementUserService.updateUser(id, oldPassword, firstPassword, secondPassword);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		return result;
	}
	
	@RequestMapping(value = "/users/add", method = RequestMethod.POST)
	public JSONObject addUser(Model model, @RequestParam("username") String username,
			@RequestParam("firstPassword") String firstPassword,
			@RequestParam("secondPassword") String secondPassword) {
		JSONObject result = new JSONObject();
		try {
			result = managementUserService.addUser(username.toLowerCase(), firstPassword, secondPassword);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value = "/users/checkUser", method = RequestMethod.POST)
	public String checkUser(Model model, @RequestParam("username") String username) {
		boolean result = managementUserService.checkUser(username);
		return Boolean.toString(result);
	}
	

	
}
