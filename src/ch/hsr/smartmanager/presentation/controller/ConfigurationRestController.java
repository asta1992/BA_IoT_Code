package ch.hsr.smartmanager.presentation.controller;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.hsr.smartmanager.service.ConfigurationService;

@RestController
@RequestMapping("/configurations")
public class ConfigurationRestController {
	
	@Autowired
	ConfigurationService configService;
	
	@RequestMapping(value = "/add", method=RequestMethod.GET)
	public void addConfiguration(@RequestParam("value") JSONArray value) {
		configService.saveConfiguration(value);
	}

	@RequestMapping(value = "/delete", method=RequestMethod.GET)
	public void deleteConfiguration(@RequestParam("value") String value) {
		configService.deleteConfiguration(value);
	}


}
