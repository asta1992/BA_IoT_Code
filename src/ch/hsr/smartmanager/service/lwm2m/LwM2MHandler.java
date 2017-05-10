package ch.hsr.smartmanager.service.lwm2m;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.eclipse.leshan.Link;
import org.eclipse.leshan.core.model.ResourceModel;
import org.eclipse.leshan.core.request.DiscoverRequest;
import org.eclipse.leshan.core.request.ExecuteRequest;
import org.eclipse.leshan.core.request.ReadRequest;
import org.eclipse.leshan.core.request.WriteRequest;
import org.eclipse.leshan.core.response.DiscoverResponse;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.eclipse.leshan.server.californium.impl.LeshanServer;
import org.eclipse.leshan.server.registration.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hsr.smartmanager.service.DeviceService;

@Service
public class LwM2MHandler {

	@Autowired
	private LwM2MManagementServer lwM2MManagementServer;
	
	@Autowired
	private DeviceService deviceService;

	private LeshanServer server;

	public LwM2MHandler() {
	}

	public WriteResponse write(String id, int objectId, int objectInstanceId, int resourceId, String value) {
		server = lwM2MManagementServer.getServer();

		Registration reg = server.getRegistrationService().getById(deviceService.getDevice(id).getRegId());
		ResourceModel.Type type = server.getModelProvider().getObjectModel(reg).getResourceModel(objectId, resourceId).type;

		WriteRequest req = getWriteRequest(objectId, objectInstanceId, resourceId, value, type);
		WriteResponse res;

		try {
			res = server.send(server.getRegistrationService().getById(deviceService.getDevice(id).getRegId()), req);
		} catch (InterruptedException e) {
			res = null;
			e.printStackTrace();
		}
		return res;
	}


	public ReadResponse read(String id, int objectId) {
		ReadRequest req = new ReadRequest(objectId);
		ReadResponse res;
		server = lwM2MManagementServer.getServer();

		try {
			res = server.send(server.getRegistrationService().getById(deviceService.getDevice(id).getRegId()), req);
		} catch (InterruptedException e) {
			res = null;
			e.printStackTrace();
		}
		return res;
	}
	
	public ReadResponse read(String id, int objectId, int objectInstanceId, int resourceId) {
		ReadRequest req = new ReadRequest(objectId + "/" + objectInstanceId + "/" + resourceId);
		ReadResponse res;
		server = lwM2MManagementServer.getServer();

		try {
			res = server.send(server.getRegistrationService().getById(deviceService.getDevice(id).getRegId()), req);
		} catch (InterruptedException e) {
			res = null;
			e.printStackTrace();
		}
		return res;
	}

	public ExecuteResponse execute(String id, int objectId, int objectInstanceId, int resourceId) {
		ExecuteRequest req = new ExecuteRequest(objectId, objectInstanceId, resourceId);
		ExecuteResponse res;
		server = lwM2MManagementServer.getServer();

		try {
			res = server.send(server.getRegistrationService().getById(deviceService.getDevice(id).getRegId()), req);
		} catch (InterruptedException e) {
			res = null;
			e.printStackTrace();
		}
		return res;
	}

	public Link[] ressourceDiscovery(String path, String id) {
		DiscoverRequest req = new DiscoverRequest(path);
		DiscoverResponse res;
		server = lwM2MManagementServer.getServer();

		try {
			res = server.send(server.getRegistrationService().getById(deviceService.getDevice(id).getRegId()), req);
		} catch (InterruptedException e) {
			res = null;
			e.printStackTrace();
		}
		return res.getObjectLinks();
	}

	private WriteRequest getWriteRequest(int objectId, int objectInstanceId, int resourceId, String value,
			ResourceModel.Type type) {
		switch (type) {
		case STRING:
			return new WriteRequest(objectId, objectInstanceId, resourceId, (String) value);
		case INTEGER:
			return new WriteRequest(objectId, objectInstanceId, resourceId, Integer.parseInt(value));
		case FLOAT:
			return new WriteRequest(objectId, objectInstanceId, resourceId, Float.parseFloat(value));
		case BOOLEAN:
			return new WriteRequest(objectId, objectInstanceId, resourceId, Boolean.parseBoolean(value));
		case OPAQUE:
			return new WriteRequest(objectId, objectInstanceId, resourceId, Byte.parseByte(value));
		case TIME: {
			SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy H:mm:ss a", Locale.ENGLISH);
			Date date;
			try {
				date = formatter.parse(value);
			} catch (ParseException e) {
				date = null;
				e.printStackTrace();
			}
			return new WriteRequest(objectId, objectInstanceId, resourceId, date);
		}
		case OBJLNK:
			return new WriteRequest(objectId, objectInstanceId, resourceId, value);
		default:
			return new WriteRequest(objectId, objectInstanceId, resourceId, "");
		}

	}
}
