package ch.hsr.smartmanager.service.lwm2m;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.eclipse.leshan.ResponseCode;
import org.eclipse.leshan.core.model.ResourceModel;
import org.eclipse.leshan.core.request.ExecuteRequest;
import org.eclipse.leshan.core.request.ReadRequest;
import org.eclipse.leshan.core.request.WriteRequest;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.eclipse.leshan.server.californium.impl.LeshanServer;
import org.eclipse.leshan.server.registration.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import ch.hsr.smartmanager.data.Device;

@Service
public class LwM2MHandler {

	@Autowired
	private LwM2MManagementServer lwM2MManagementServer;

	public LwM2MHandler() {
	}

	public ReadResponse read(Device device, int objectId) {
		LeshanServer server = lwM2MManagementServer.getServer();

		Registration registration = server.getRegistrationService().getById(device.getRegId());
		if (registration == null) {
			return new ReadResponse(ResponseCode.NOT_FOUND, null, "Device is not reachable");
		}

		ReadRequest req = new ReadRequest(objectId);
		ReadResponse res;

		try {
			res = server.send(registration, req);
		} catch (InterruptedException e) {
			res = new ReadResponse(ResponseCode.NOT_FOUND, null, "Device is not reachable");
		}

		return res;
	}

	public ReadResponse read(Device device, int objectId, int objectInstanceId, int resourceId) {
		LeshanServer server = lwM2MManagementServer.getServer();

		Registration registration = server.getRegistrationService().getById(device.getRegId());
		if (registration == null) {
			return new ReadResponse(ResponseCode.NOT_FOUND, null, "Device is not reachable");
		}

		ReadRequest req = new ReadRequest(objectId + "/" + objectInstanceId + "/" + resourceId);
		ReadResponse res;

		try {
			res = server.send(registration, req);
		} catch (InterruptedException e) {
			res = new ReadResponse(ResponseCode.NOT_FOUND, null, "Device is not reachable");
		}

		return res;

	}

	public WriteResponse write(Device device, int objectId, int objectInstanceId, int resourceId, String value) {
		LeshanServer server = lwM2MManagementServer.getServer();

		Registration registration = server.getRegistrationService().getById(device.getRegId());
		if (registration == null) {
			return new WriteResponse(ResponseCode.NOT_FOUND, "Device is not reachable");
		}

		ResourceModel.Type type = server.getModelProvider().getObjectModel(registration).getResourceModel(objectId,
				resourceId).type;
		WriteRequest req = getWriteRequest(objectId, objectInstanceId, resourceId, HtmlUtils.htmlEscape(value), type);
		WriteResponse res;

		try {
			res = server.send(registration, req);
		} catch (InterruptedException e) {
			res = new WriteResponse(ResponseCode.NOT_FOUND, "Device is not reachable");
		}

		return res;
	}

	public ExecuteResponse execute(Device device, int objectId, int objectInstanceId, int resourceId) {
		LeshanServer server = lwM2MManagementServer.getServer();

		Registration registration = server.getRegistrationService().getById(device.getRegId());
		if (registration == null) {
			return new ExecuteResponse(ResponseCode.NOT_FOUND, "Device is not reachable");
		}

		ExecuteRequest req = new ExecuteRequest(objectId, objectInstanceId, resourceId);
		ExecuteResponse res;

		try {
			res = server.send(registration, req);
		} catch (InterruptedException e) {
			res = new ExecuteResponse(ResponseCode.NOT_FOUND, "Device is not reachable");
		}

		return res;
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
				date = new Date();
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
