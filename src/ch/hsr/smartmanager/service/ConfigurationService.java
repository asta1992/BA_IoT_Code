package ch.hsr.smartmanager.service;

import java.util.List;

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
			Configuration configuration = new Configuration(config.getString(0), config.getString(1));
			for (int i = 2; i < config.length(); i++) {
				String path = (String)config.getJSONObject(i).get("Object Link");
				String value = (String)config.getJSONObject(i).get("Value");
				ConfigurationItem configurationItem = new ConfigurationItem(path, value);
				configuration.add(configurationItem);
			}
			configRepo.save(configuration);
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
	
	public void writeConfigurationToGroup(String id, Configuration configuration) {
		List<Device> devices = deviceService.findAllChildren(id);
		for(Device device : devices) {
			writeConfigurationToDevice(device.getId(), configuration);
		}
	}
	
	public void writeConfigurationToDevice(String id, Configuration configuration) {
		for(ConfigurationItem item : configuration.getConfigurationItems()) {
			lwM2MHandler.write(id, item.getPathPart(1), item.getPathPart(2), item.getPathPart(3), item.getValue());
		}
	}
		
	

}
