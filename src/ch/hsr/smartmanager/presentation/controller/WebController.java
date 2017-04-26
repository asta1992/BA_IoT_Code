package ch.hsr.smartmanager.presentation.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.ResourceModel;
import org.eclipse.leshan.core.observation.Observation;
import org.eclipse.leshan.server.registration.Registration;
import org.eclipse.leshan.server.registration.RegistrationListener;
import org.eclipse.leshan.server.registration.RegistrationUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.hsr.smartmanager.data.Device;
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
		Iterator<Registration> a = lwM2MManagementServer.getServer().getRegistrationService().getAllRegistrations();
		List<Registration> devList = new ArrayList<>();
		while(a.hasNext()) {
			devList.add(a.next());
		}
		model.addAttribute("devices", devList);
		return "index";
	}
	
	@RequestMapping(value = "/devices", method = RequestMethod.POST)
	public String saveOrUpdateUser(@ModelAttribute Device device, Model model, BindingResult result) {
		//deviceService.createOrUpdateDevice(device);
		return "redirect:/";
	}

	@RequestMapping(value = "/devices/add", method = RequestMethod.GET)
	public String showAddUser(Model model) {
		model.addAttribute("authType", deviceService.getAuthType());
		model.addAttribute("protocolType", deviceService.getProtocolType());
		model.addAttribute("device", new Device());
		return "createDevice";
	}

	@RequestMapping(value = "/devices/{id}/update", method = RequestMethod.GET)
	public String showUpdateUserForm(@PathVariable("id") String id, Device device, Model model) {
		model.addAttribute("authType", deviceService.getAuthType());
		model.addAttribute("protocolType", deviceService.getProtocolType());
		model.addAttribute("device", deviceService.getDevice(id));
		return "createDevice";
	}

	@RequestMapping(value = "/devices/{id}/delete", method = RequestMethod.POST)
	public String deleteUser(@PathVariable("id") String id, Model model) {
		deviceService.deleteDevice(id);
		return "redirect:/";
	}

	@RequestMapping(value = "/devices/{id}", method = RequestMethod.GET)
	public String showDeviceDetails(Model model, @PathVariable("id") String id) {

		Collection<ObjectModel> lwm2mModel = lwM2MManagementServer.getServer().getModelProvider()
				.getObjectModel(lwM2MManagementServer.getServer().getRegistrationService().getById(id))
				.getObjectModels();

		Collection<ResourceModel> resourceModel = null;
		for (ObjectModel objectmodel : lwm2mModel) {
			if (objectmodel.id == 3) {
				resourceModel = objectmodel.resources.values();
			}
		}
		Map<Integer, String> resourceName = new HashMap<>();
		Map<Integer, String> resourceCommand = new HashMap<>();

		for(ResourceModel s : resourceModel) {
			resourceName.put(s.id, s.name);
			resourceCommand.put(s.id, s.operations.toString());
		}
		
		model.addAttribute("name", resourceName);
		model.addAttribute("operation", resourceCommand);
		model.addAttribute("devID", id);

		return "deviceView";
		}

	@RequestMapping(value = { "/discovery", "/accountsettings", "/settings" })
	public String showTodo(Model model) {

		return "todo";
	}
}
