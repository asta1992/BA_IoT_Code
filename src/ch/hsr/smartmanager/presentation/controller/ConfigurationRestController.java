package ch.hsr.smartmanager.presentation.controller;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.hsr.smartmanager.service.ConfigurationService;

@RestController
@RequestMapping("/configurations")
public class ConfigurationRestController {
	
	@Autowired
	ConfigurationService configService;
	
	@RequestMapping(value = "/add")
	public void addConfiguration(Model model, @RequestParam("value") JSONArray value) {
		configService.saveConfiguration(value);
	}

	@RequestMapping(value = "/delete")
	public void deleteConfiguration(Model model, @RequestParam("value") String value) {
		configService.deleteConfiguration(value);
	}


}
