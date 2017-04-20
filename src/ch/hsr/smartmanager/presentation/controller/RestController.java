package ch.hsr.smartmanager.presentation.controller;

import org.eclipse.leshan.core.response.ReadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.hsr.smartmanager.service.DeviceService;
import ch.hsr.smartmanager.service.lwm2m.LwM2MHandler;
import ch.hsr.smartmanager.service.lwm2m.LwM2MManagementServer;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private LwM2MManagementServer lwM2MManagementServer;
	@Autowired
	LwM2MHandler lwM2MHandler;
	
	@RequestMapping(value = "/devices/{id}/read/{res}/{resid}/{resval}", method = RequestMethod.GET)
	public ReadResponse read(Model model, @PathVariable("id") String id, @PathVariable("res") String res,
			@PathVariable("resid") String resid, @PathVariable("resval") String resval) {

		String path = res + "/" + resid + "/" + resval;
		return lwM2MHandler.read(path, id);
	}


}
