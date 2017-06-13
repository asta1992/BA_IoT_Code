package ch.hsr.smartmanager.service.lwm2mservices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.leshan.ResponseCode;
import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.ResourceModel;
import org.eclipse.leshan.core.request.ExecuteRequest;
import org.eclipse.leshan.core.request.ReadRequest;
import org.eclipse.leshan.core.request.WriteRequest;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.eclipse.leshan.server.californium.impl.LeshanServer;
import org.eclipse.leshan.server.model.StaticModelProvider;
import org.eclipse.leshan.server.registration.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.ResourceModelAdapter;

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

		ResourceModel type = server.getModelProvider().getObjectModel(registration).getResourceModel(objectId,
				resourceId);

		WriteRequest req = getWriteRequest(objectId, objectInstanceId, resourceId, HtmlUtils.htmlEscape(value), type.type);
		WriteResponse res;
		
		
		if(req.getPath().getObjectId() == 0) {
			return new WriteResponse(ResponseCode.UNSUPPORTED_CONTENT_FORMAT, "The content format is not valid for this Resource");
		}

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
		WriteRequest req;
		
		switch (type) {
		case STRING:
			return new WriteRequest(objectId, objectInstanceId, resourceId, (String) value);
		case INTEGER:
			try {
				req =  new WriteRequest(objectId, objectInstanceId, resourceId, Integer.parseInt(value));
			} catch (NumberFormatException e) {
				req =  new WriteRequest(0, 0, 0, Integer.parseInt("0"));
			}
			return req;
		case FLOAT:
			try {
				req =  new WriteRequest(objectId, objectInstanceId, resourceId, Float.parseFloat(value));
			} catch (Exception e) {
				req =  new WriteRequest(0, 0, 0, Float.parseFloat("0"));
			}
			return req;
		case OPAQUE:
			
			try {
				req = new WriteRequest(objectId, objectInstanceId, resourceId, Byte.parseByte(value));
			} catch (NumberFormatException e) {
				req = new WriteRequest(0, 0, 0, Byte.parseByte("0"));
			}
			return req;

		case BOOLEAN:
			return new WriteRequest(objectId, objectInstanceId, resourceId, Boolean.parseBoolean(value));
		case TIME: {
			SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy H:mm:ss a", Locale.ENGLISH);
			Date date;
			try {
				date = formatter.parse(value);
				req = new WriteRequest(objectId, objectInstanceId, resourceId, date);
			} catch (ParseException e) {
				req = new WriteRequest(0, 0, 0, new Date());
			}
			return req;
		}
		case OBJLNK:
			return new WriteRequest(objectId, objectInstanceId, resourceId, value);
		default:
			return new WriteRequest(0, 0, 0, "");
		}

	}

	public Map<Integer, String> allWritableObjectIDs() {
		Map<Integer, String> map = new TreeMap<>();
		List<ObjectModel> models = lwM2MManagementServer.getModels();

		for (ObjectModel model : models) {
			for (Map.Entry<Integer, ResourceModel> resource : model.resources.entrySet()) {
				if (resource.getValue().operations.toString().contains("W")) {
					map.put(model.id, model.name);
					break;
				}
			}
		}
		return map;
	}

	public Map<Integer, String> allExecutableObjectIDs() {
		Map<Integer, String> map = new TreeMap<>();
		List<ObjectModel> models = lwM2MManagementServer.getModels();

		for (ObjectModel model : models) {
			for (Map.Entry<Integer, ResourceModel> resource : model.resources.entrySet()) {
				if (resource.getValue().operations.toString().contains("E")) {
					map.put(model.id, model.name);
					break;
				}
			}
		}
		return map;
	}

	public List<ResourceModelAdapter> allWritableResources(String objectId) {
		List<ResourceModelAdapter> resources = new ArrayList<>();
		List<ObjectModel> models = lwM2MManagementServer.getModels();

		for (ObjectModel model : models) {
			if (model.id == Integer.parseInt(objectId)) {
				for (Map.Entry<Integer, ResourceModel> resource : model.resources.entrySet()) {
					if (resource.getValue().operations.toString().contains("W")) {
						resources.add(new ResourceModelAdapter(resource.getValue()));
					}
				}
			}
		}
		return resources;
	}

	public List<ResourceModelAdapter> allExecuteableResources(String objectId) {
		List<ResourceModelAdapter> resources = new ArrayList<>();
		List<ObjectModel> models = lwM2MManagementServer.getModels();

		for (ObjectModel model : models) {
			if (model.id == Integer.parseInt(objectId)) {
				for (Map.Entry<Integer, ResourceModel> resource : model.resources.entrySet()) {
					if (resource.getValue().operations.toString().contains("E")) {
						resources.add(new ResourceModelAdapter(resource.getValue()));
					}
				}
			}
		}
		return resources;
	}

	public boolean isMultiInstance(String objectId) {
		List<ObjectModel> models = lwM2MManagementServer.getModels();
		for (ObjectModel model : models) {
			if (model.id == Integer.parseInt(objectId)) {
				return model.multiple;
			}
		}
		return false;
	}

	public LinkedHashMap<String, ArrayList<ResourceModelAdapter>> getObjectModelList(Device device) {
		LinkedHashMap<String, ArrayList<ResourceModelAdapter>> objectModelList = new LinkedHashMap<String, ArrayList<ResourceModelAdapter>>();
		ArrayList<ResourceModelAdapter> resourceModelList = new ArrayList<ResourceModelAdapter>();
		LeshanServer server = lwM2MManagementServer.getServer();

		final String regex = "\\/([0-9]*)\\/";
		final Pattern pattern = Pattern.compile(regex);
		Matcher matcher;

		if (device == null) {
			return objectModelList;
		} else {
			LwM2mModel regModel;

			Registration registration = server.getRegistrationService().getById(device.getRegId());
			if (registration == null) {
				regModel = new StaticModelProvider(lwM2MManagementServer.getModels()).getObjectModel(null);
			} else {
				regModel = server.getModelProvider().getObjectModel(registration);
			}

			for (String objId : device.getObjectLinks()) {
				matcher = pattern.matcher(objId);
				String parseId = "1";
				if (matcher.find()) {
					parseId = matcher.group(1);
				}

				ObjectModel objectModel = regModel.getObjectModel(Integer.parseInt(parseId));
				resourceModelList = new ArrayList<ResourceModelAdapter>();

				if (objectModel == null) {
					break;
				}

				for (ResourceModel entry : objectModel.resources.values()) {
					resourceModelList.add(new ResourceModelAdapter(entry));
				}
				objectModelList.put(objectModel.name, resourceModelList);
			}
			return objectModelList;
		}

	}
}
