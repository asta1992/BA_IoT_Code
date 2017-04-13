package ch.hsr.smartmanager.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ch.hsr.smartmanager.data.Credential;
import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.Type;
import ch.hsr.smartmanager.service.DeviceService;

@Controller
public class WebController {

	private DeviceService deviceService;

	@Autowired
	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	@RequestMapping(value = "/")
	public String showIndex(Model model) {
		model.addAttribute("devices", deviceService.getAllDevice());
		return "index";
	}

	@RequestMapping(value = "/createDevice")
	public String showCreateDevice(Model model) {

		return "createDevice";
	}
	

	@RequestMapping(value = "/devices/{id}")
	public String showDeviceDetails(Model model, @PathVariable("id") String id) {
		
		model.addAttribute("device", deviceService.getDevice(id));
		
		return "deviceDetails";
	}


	@RequestMapping(value = "/doCreate", method = RequestMethod.POST)
	public String doCreate(@RequestParam("name") String name, @RequestParam("protocolType") String protocolType,
			@RequestParam("authType") String authType, @RequestParam("ipAddress") String ipAddress,
			@RequestParam("username") String username, @RequestParam("password") String password) {

		System.out.println(deviceService.createDevice(new Device(name, deviceService.createType(new Type(protocolType, authType)), ipAddress, deviceService.createCredential(new Credential(username, password)))));

		return "index";
	}

}
