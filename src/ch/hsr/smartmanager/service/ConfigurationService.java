package ch.hsr.smartmanager.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hsr.smartmanager.data.Configuration;
import ch.hsr.smartmanager.data.ConfigurationItem;
import ch.hsr.smartmanager.data.repository.ConfigurationItemRepository;

@Service
public class ConfigurationService {

	@Autowired
	private ConfigurationItemRepository configRepo;

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

	public void deleteConfiguration(String configurationId) {
		configRepo.delete(configRepo.findOne(configurationId));
	}

}
