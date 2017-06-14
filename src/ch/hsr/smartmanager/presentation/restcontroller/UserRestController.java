package ch.hsr.smartmanager.presentation.restcontroller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import ch.hsr.smartmanager.service.applicationservices.UserService;

@RestController
@RequestMapping("/users")
public class UserRestController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteUser(@RequestParam("username") String username) {
		boolean result = userService.deleteUser(username);
		return Boolean.toString(result);
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
	public JSONObject editUser(@PathVariable("id") String id, @RequestParam("oldPassword") String oldPassword,
			@RequestParam("firstPassword") String firstPassword,
			@RequestParam("secondPassword") String secondPassword) {

		JSONObject result = new JSONObject();
		result = userService.updateUser(id, oldPassword, firstPassword, secondPassword);
		return result;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JSONObject addUser(@RequestParam("username") String username,
			@RequestParam("firstPassword") String firstPassword,
			@RequestParam("secondPassword") String secondPassword) {
		JSONObject result = new JSONObject();
		result = userService.addUser(HtmlUtils.htmlEscape(username.toLowerCase()), firstPassword, secondPassword);
		return result;
	}

}
