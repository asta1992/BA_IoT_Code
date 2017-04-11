package ch.hsr.smartmanager.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.hsr.smartmanager.service.DeviceService;


@Controller
public class WebController {
	
	private DeviceService deviceService;
		
	@Autowired
	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}



	@RequestMapping(value="/")
	public String showIndex(Model model){	
		
		model.addAttribute("devices", deviceService.getAllDevice());

		
		return "index";
	}

}
