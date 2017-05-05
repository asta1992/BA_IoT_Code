package ch.hsr.smartmanager.presentation.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.leshan.Link;
import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.ResourceModel;
import org.eclipse.leshan.server.registration.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.ResourceModelAdapter;
import ch.hsr.smartmanager.service.DeviceService;
import ch.hsr.smartmanager.service.lwm2m.LwM2MHandler;
import ch.hsr.smartmanager.service.lwm2m.LwM2MManagementServer;

@Controller
public class WebController {

	@Autowired
	private DeviceService deviceService;
	@Autowired
	private LwM2MManagementServer lwM2MManagementServer;
	@Autowired
	LwM2MHandler lwM2MHandler;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showIndex(Model model) {

		model.addAttribute("devices", deviceService.getAllDiscoveredDevice());
		return "index";
	}

	@RequestMapping(value = "/devices/{id}/add", method = RequestMethod.GET)
	public String addDevice(Model model, @PathVariable("id") String id) {
		deviceService.toggleDevice(id);
		return "redirect:/discovery";
	}

	@RequestMapping(value = "/devices/{id}/delete", method = RequestMethod.GET)
	public String removeDevice(Model model, @PathVariable("id") String id) {
		deviceService.toggleDevice(id);
		return "redirect:/";
	}


	@RequestMapping(value = "/discovery")
	public String showDiscovery(Model model) {
		model.addAttribute("devices", deviceService.getAllDiscoveredDevice());
		return "discovery";
	}

	@RequestMapping(value = "/devices/{id}", method = RequestMethod.GET)
	public String showDevices(Model model, @PathVariable("id") String id) {

		Registration reg = lwM2MManagementServer.getServer().getRegistrationService().getById(id);
		Device dev = deviceService.getDevice(id);
		
		LwM2mModel regModel = lwM2MManagementServer.getServer().getModelProvider().getObjectModel(reg);
		
		HashMap<String, ArrayList<ResourceModelAdapter>> objectModelList = new HashMap<String, ArrayList<ResourceModelAdapter>>();
		ArrayList<ResourceModelAdapter> resourceModelList = new ArrayList<ResourceModelAdapter>();

		for (int objId : dev.getObjectLinks()) {
			ObjectModel objectModel = regModel.getObjectModel(objId);
			resourceModelList = new ArrayList<ResourceModelAdapter>();
			
			for (ResourceModel entry : objectModel.resources.values())
			{
				resourceModelList.add(new ResourceModelAdapter(entry));
			}
			objectModelList.put(objectModel.name, resourceModelList);
		}
		

		model.addAttribute("modelDescription", objectModelList);
		model.addAttribute("registration", reg);
		model.addAttribute("id", id);
		return "devices";
	}

	@RequestMapping(value = "/devices/{id}/summary", method = RequestMethod.GET)
	public ModelAndView deviceSummary(@PathVariable("id") String id) {
		ModelAndView model = new ModelAndView("deviceSummary");
		return model;
	}

	// OLD--------------------

	@RequestMapping(value = "/devices/{id}/update", method = RequestMethod.GET)
	public String showUpdateUserForm(@PathVariable("id") String id, Device device, Model model) {
		model.addAttribute("device", deviceService.getDevice(id));
		return "createDevice";
	}

	@RequestMapping(value = "/devices/{id}/delete", method = RequestMethod.POST)
	public String deleteUser(@PathVariable("id") String id, Model model) {
		deviceService.deleteDevice(id);
		return "redirect:/";
	}

	@RequestMapping(value = "/devices", method = RequestMethod.POST)
	public String saveOrUpdateUser(@ModelAttribute Device device, Model model, BindingResult result) {
		// deviceService.createOrUpdateDevice(device);
		return "redirect:/";
	}


}
