package ch.hsr.smartmanager.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.service.DeviceService;

@Controller
public class WebController {

	@Autowired
	private DeviceService deviceService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showIndex(Model model) {
		model.addAttribute("devices", deviceService.getAllDevice());
		return "index";
	}
	

	
	@RequestMapping(value = "/devices", method = RequestMethod.POST)
	public String saveOrUpdateUser(@ModelAttribute("userForm") @Validated Device device, BindingResult result, Model model) {
		deviceService.createOrUpdateDevice(device);
		return "index";
	}
	
	
	
	@RequestMapping(value = "/devices/add", method= RequestMethod.GET)
	public String showAddUser(Model model) {
				
		model.addAttribute("authType", deviceService.getAuthType());
		model.addAttribute("protocolType", deviceService.getProtocolType());
		model.addAttribute("deviceForm", new Device());
		return "createDevice";
	}
	
	@RequestMapping(value = "/devices/{id}/update", method = RequestMethod.GET)
	public String showUpdateUserForm(@PathVariable("id") String id, Model model) {
		Device device = deviceService.getDevice(id);
		model.addAttribute("deviceForm", device);
		
		return "createDevice";
	}
	
	@RequestMapping(value="/devices/{id}/delete", method=RequestMethod.POST)
	public String deleteUser(@PathVariable("id") String id) {
		deviceService.deleteDevice(id);
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/devices/{id}", method = RequestMethod.GET)
	public String showDeviceDetails(Model model, @PathVariable("id") String id) {

		model.addAttribute("device", deviceService.getDevice(id));
		return "deviceDetails";
	}
	
	@RequestMapping(value = { "/discovery", "/accountsettings", "/settings" })
	public String showTodo(Model model) {

		return "todo";
	}
}
