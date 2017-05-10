package ch.hsr.smartmanager.presentation.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.ResourceModel;
import org.eclipse.leshan.server.registration.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.DeviceGroup;
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
		
		Device dev1 = new Device("Dev1");
		Device dev2 = new Device("Dev2");
		Device dev3 = new Device("Dev3");
		
		DeviceGroup grp1 = new DeviceGroup("grp1");
		DeviceGroup grp2 = new DeviceGroup("grp2");
		DeviceGroup grp3 = new DeviceGroup("grp3");
		DeviceGroup grp4 = new DeviceGroup("grp4");
		DeviceGroup grp5 = new DeviceGroup("grp5");
		DeviceGroup grp6 = new DeviceGroup("grp6");

		
		dev1 = deviceService.insertDevice(dev1);
		dev2 = deviceService.insertDevice(dev2);
		dev3 = deviceService.insertDevice(dev3);
		
		grp1 = deviceService.insertGroup(grp1);
		grp2 = deviceService.insertGroup(grp2);
		grp3 = deviceService.insertGroup(grp3);
		grp4 = deviceService.insertGroup(grp4);
		grp5 = deviceService.insertGroup(grp5);
		grp6 = deviceService.insertGroup(grp6);
		
		deviceService.addGroupToGroup(grp1.getId(), grp2.getId());
		deviceService.addGroupToGroup(grp2.getId(), grp3.getId());
		deviceService.addGroupToGroup(grp3.getId(), grp4.getId());
		deviceService.addGroupToGroup(grp4.getId(), grp5.getId());
		deviceService.addGroupToGroup(grp2.getId(), grp5.getId());
		
		deviceService.addDeviceToGroup(grp1.getId(), dev1.getId());
		deviceService.addDeviceToGroup(grp3.getId(), dev1.getId());
		deviceService.addDeviceToGroup(grp3.getId(), dev3.getId());
		deviceService.addDeviceToGroup(grp1.getId(), dev2.getId());
		
	
		return "index";
	}

	@RequestMapping(value = "/discovery")
	public String showDiscovery(Model model) {
		model.addAttribute("devices", deviceService.getAllDiscoveredDevice());
		return "discovery";
	}
	
	@RequestMapping(value = "/devices")
	public String showDevices(Model model) {
		return "devices";
	}
	
	@RequestMapping(value = "/devices/{id}", method = RequestMethod.GET)
	public String showDeviceDetails(Model model, @PathVariable("id") String id) {

		Device dev = deviceService.getDevice(id);
		Registration reg = lwM2MManagementServer.getServer().getRegistrationService().getById(dev.getRegId());

		LwM2mModel regModel = lwM2MManagementServer.getServer().getModelProvider().getObjectModel(reg);

		LinkedHashMap<String, ArrayList<ResourceModelAdapter>> objectModelList = new LinkedHashMap<String, ArrayList<ResourceModelAdapter>>();
		ArrayList<ResourceModelAdapter> resourceModelList = new ArrayList<ResourceModelAdapter>();

		for (int objId : dev.getObjectLinks()) {
			ObjectModel objectModel = regModel.getObjectModel(objId);
			resourceModelList = new ArrayList<ResourceModelAdapter>();

			for (ResourceModel entry : objectModel.resources.values()) {
				resourceModelList.add(new ResourceModelAdapter(entry));
			}
			objectModelList.put(objectModel.name, resourceModelList);
		}
		
		model.addAttribute("modelDescription", objectModelList);
		model.addAttribute("objectLinks", dev.getObjectLinks().toArray());
		model.addAttribute("registration", reg);
		model.addAttribute("device", dev);
		
		return "fragments/deviceFragment";
	}
	
	@RequestMapping(value = "/groups/{id}", method = RequestMethod.GET)
	public String showGroupDetails(Model model, @PathVariable("id") String id) {
		DeviceGroup group = deviceService.getGroup(id);
		
		model.addAttribute("group", group);
		return "fragments/groupFragment";
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
}
