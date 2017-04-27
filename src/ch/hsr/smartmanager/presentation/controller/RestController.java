package ch.hsr.smartmanager.presentation.controller;

import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ch.hsr.smartmanager.service.DeviceService;
import ch.hsr.smartmanager.service.lwm2m.LwM2MHandler;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@Autowired
	LwM2MHandler lwM2MHandler;
	
	@Autowired
	DeviceService deviceService;
	
	@RequestMapping(value = "/devices/{id}/read/{objectId}/{objectInstanceId}/{resourceId}", method = RequestMethod.GET)
	public ReadResponse read(Model model, 
			@PathVariable("id") String id, 
			@PathVariable("objectId") int objectId, 
			@PathVariable("objectInstanceId") int objectInstanceId,
			@PathVariable("resourceId") int resourceId) {

		return lwM2MHandler.read(id, objectId, objectInstanceId, resourceId);
	}
	
	@RequestMapping(value = "/devices/{id}/write/{objectId}/{objectInstanceId}/{resourceId}", method = RequestMethod.POST)
	public WriteResponse write(Model model, 
			@PathVariable("id") String id,
			@PathVariable("objectId") int objectId, 
			@PathVariable("objectInstanceId") int objectInstanceId,
			@PathVariable("resourceId") int resourceId,
			@RequestParam("postValue")String postValue  ) {
		
		return lwM2MHandler.write(id, objectId, objectInstanceId, resourceId, postValue);
	}
	
	@RequestMapping(value = "/devices/{id}/execute/{objectId}/{objectInstanceId}/{resourceId}", method = RequestMethod.GET)
	public ExecuteResponse execute(Model model, 
			@PathVariable("id") String id,
			@PathVariable("objectId") int objectId, 
			@PathVariable("objectInstanceId") int objectInstanceId,
			@PathVariable("resourceId") int resourceId){
		
		return lwM2MHandler.execute(id, objectId, objectInstanceId, resourceId);
	}
	
	@RequestMapping(value = "/countDiscoveredDevices", method = RequestMethod.GET)
	public int countDevices(Model model){
		
		return deviceService.countDiscoveredDevices();
	}
	

}
