package ch.hsr.smartmanager.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.leshan.ResponseCode;
import org.eclipse.leshan.core.response.WriteResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hsr.smartmanager.data.Configuration;
import ch.hsr.smartmanager.data.ConfigurationItem;
import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.repository.ConfigurationItemRepository;
import ch.hsr.smartmanager.service.lwm2m.LwM2MHandler;
import ch.hsr.smartmanager.service.lwm2m.LwM2MManagementServer;

@Service
public class ConfigurationService {

	@Autowired
	private ConfigurationItemRepository configRepo;
	
	@Autowired
	private DeviceService deviceService;

	@Autowired
	private LwM2MHandler lwM2MHandler;

	public void saveConfiguration(JSONArray config) {
		
		try {
			String configurationName = config.getString(0);
			String configurationDescription = config.getString(1);
			Configuration configuration = new Configuration(configurationName, configurationDescription);
			for (int i = 2; i < config.length(); i++) {
				String path = (String)config.getJSONObject(i).get("Object Link");
				String value = (String)config.getJSONObject(i).get("Value");
				ConfigurationItem configurationItem = new ConfigurationItem(path, value);
				configuration.add(configurationItem);
			}
			if (configRepo.existsByName(configurationName)) {
				Configuration updatedConfiguration = configRepo.findByName(configurationName);
				configuration.setId(updatedConfiguration.getId());
				updatedConfiguration = configuration;
				configRepo.save(updatedConfiguration);
			} else {
				configRepo.insert(configuration);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public List<Configuration> getAllConfigurations(){
		return configRepo.findAll();
	}
	
	public Configuration findOne(String id){
		return configRepo.findOne(id);
	}

	public void deleteConfiguration(String configurationId) {
		configRepo.delete(configRepo.findOne(configurationId));
	}
	
	public Map<String, List<Map<String, ResponseCode>>> writeConfigurationToGroup(String groupId, String configurationId) {
		Map<String, List<Map<String, ResponseCode>>> responseMap = new HashMap<>();
		List<Device> devices = deviceService.findAllChildren(groupId);
		for(Device device : devices) {
			responseMap.put(device.getName(), writeConfigurationToDevice(device.getId(), configurationId));
		}
		return responseMap;
	}
	
	public List<Map<String, ResponseCode>> writeConfigurationToDevice(String deviceId, String configurationId) {
		List<Map<String, ResponseCode>> responseList = new ArrayList<>();
		Configuration configuration = configRepo.findOne(configurationId);
		for(ConfigurationItem item : configuration.getConfigurationItems()) {
			responseList.add(lwM2MHandler.write(deviceId, item.getPathPart(1), item.getPathPart(2), item.getPathPart(3), item.getValue()));
		}
		return responseList;
	}
}
