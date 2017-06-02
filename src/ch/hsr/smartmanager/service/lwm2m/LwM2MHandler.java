package ch.hsr.smartmanager.service.lwm2m;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.eclipse.leshan.ResponseCode;
import org.eclipse.leshan.core.model.ResourceModel;
import org.eclipse.leshan.core.node.LwM2mMultipleResource;
import org.eclipse.leshan.core.node.LwM2mObject;
import org.eclipse.leshan.core.node.LwM2mObjectInstance;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.node.LwM2mSingleResource;
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

	public ReadResponse read(String id, int objectId) {
		ReadRequest req = new ReadRequest(objectId);
		ReadResponse res;
		server = lwM2MManagementServer.getServer();
		Device dev = deviceService.getDevice(id);

		Registration registration = server.getRegistrationService().getById(deviceService.getDevice(id).getRegId());

		if (registration == null)
			return new ReadResponse(ResponseCode.NOT_FOUND, null, "Device is not reachable");

		try {
			res = server.send(registration, req);
		} catch (InterruptedException e) {
			res = null;
			e.printStackTrace();
		}

		saveMultipleValueToDevice(res, dev, objectId);

		return res;
	}

	public ReadResponse read(String id, int objectId, int objectInstanceId, int resourceId) {
		ReadRequest req = new ReadRequest(objectId + "/" + objectInstanceId + "/" + resourceId);
		ReadResponse res;
		server = lwM2MManagementServer.getServer();
		Device dev = deviceService.getDevice(id);
		Registration registration = server.getRegistrationService().getById(deviceService.getDevice(id).getRegId());

		if (registration == null)
			return new ReadResponse(ResponseCode.NOT_FOUND, null, "Device is not reachable");

		try {
			res = server.send(registration, req);
		} catch (InterruptedException e) {
			res = null;
			e.printStackTrace();
		}

		saveValueToDevice(res, dev,
				Integer.toString(objectId) + Integer.toString(objectInstanceId) + Integer.toString(resourceId));

		return res;

	}

	private void saveMultipleValueToDevice(ReadResponse res, Device dev, int objectId) {

		if (res.getContent() == null)
			return;

		String path = Integer.toString(objectId);

		LwM2mObject node = (LwM2mObject) res.getContent();

		Map<Integer, LwM2mObjectInstance> instance = node.getInstances();
		Map<String, String> dataMap = dev.getDataMap();

		for (Map.Entry<Integer, LwM2mObjectInstance> entry : instance.entrySet()) {
			Map<Integer, LwM2mResource> inst = entry.getValue().getResources();

			for (Map.Entry<Integer, LwM2mResource> resource : inst.entrySet()) {
				if (resource.getValue() instanceof LwM2mSingleResource) {
					LwM2mSingleResource singleRes = (LwM2mSingleResource) resource.getValue();

					dataMap.put(path + entry.getKey() + resource.getKey(), singleRes.getValue().toString());

				} else if (resource.getValue() instanceof LwM2mMultipleResource) {
					LwM2mMultipleResource resources = (LwM2mMultipleResource) resource.getValue();
					Map<Integer, ?> a = resources.getValues();
					dataMap.put(path, a.entrySet().toString());
				}
			}

		}

		dev.setLastUpdate(new Date());
		dev.setDataMap(dataMap);
		deviceService.updateDevice(dev);
	}

	private void saveValueToDevice(ReadResponse res, Device dev, String path) {

		Map<String, String> dataMap = dev.getDataMap();

		if (res.getContent() != null) {
			if (res.getContent() instanceof LwM2mSingleResource) {
				LwM2mSingleResource resource = (LwM2mSingleResource) res.getContent();

				dataMap.put(path, resource.getValue().toString());

			} else if (res.getContent() instanceof LwM2mMultipleResource) {
				LwM2mMultipleResource resources = (LwM2mMultipleResource) res.getContent();

				dataMap.put(path, resources.getValues().toString());
			}
		}
		dev.setLastUpdate(new Date());
		dev.setDataMap(dataMap);
		deviceService.updateDevice(dev);
	}

	public Map<String, ResponseCode> write(String id, int objectId, int objectInstanceId, int resourceId,
			String value) {
		server = lwM2MManagementServer.getServer();

		Map<String, ResponseCode> response = new HashMap<>();

		Registration registration = server.getRegistrationService().getById(deviceService.getDevice(id).getRegId());

		if (registration == null) {
			response.put(objectId + "/" + objectInstanceId + "/" + resourceId, ResponseCode.NOT_FOUND);
			return response;
		}

		ResourceModel.Type type = server.getModelProvider().getObjectModel(registration).getResourceModel(objectId,
				resourceId).type;

		WriteRequest req = getWriteRequest(objectId, objectInstanceId, resourceId, HtmlUtils.htmlEscape(value), type);
		WriteResponse res;

		try {
			res = server.send(registration, req);
		} catch (InterruptedException e) {
			res = null;
			e.printStackTrace();
		}

		if (res.getCode() == ResponseCode.CHANGED) {
			read(id, objectId, objectInstanceId, resourceId);
		}

		response.put(objectId + "/" + objectInstanceId + "/" + resourceId, res.getCode());
		return response;
	}

	public List<Map<String, ResponseCode>> writeToAllChildren(String id, int objectId, int objectInstanceId,
			int resourceId, String value) {
		List<Map<String, ResponseCode>> responses = new ArrayList<>();
		List<Device> devices = deviceService.findAllChildren(id);
		for (Device dev : devices) {
			responses.add(write(dev.getId(), objectId, objectInstanceId, resourceId, value));
		}
		return responses;

	}

	public ExecuteResponse execute(String id, int objectId, int objectInstanceId, int resourceId) {
		ExecuteRequest req = new ExecuteRequest(objectId, objectInstanceId, resourceId);
		ExecuteResponse res;
		server = lwM2MManagementServer.getServer();
		Registration registration = server.getRegistrationService().getById(deviceService.getDevice(id).getRegId());
		
		if(registration == null) {
			return new ExecuteResponse(ResponseCode.NOT_FOUND, null, "Device is not reachable");
		}

		try {
			res = server.send(registration, req);
		} catch (InterruptedException e) {
			res = null;
			e.printStackTrace();
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
