package ch.hsr.smartmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.ResourceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hsr.smartmanager.data.ResourceModelAdapter;
import ch.hsr.smartmanager.service.lwm2m.LwM2MManagementServer;

@Service
public class LwMwMService {
	
	@Autowired
	private LwM2MManagementServer lwM2MManagementServer;

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

	
	
	
}
