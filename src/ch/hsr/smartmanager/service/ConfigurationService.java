package ch.hsr.smartmanager.service;

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
			Configuration configuration = new Configuration(config.getString(0));
			for (int i = 1; i < config.length(); i++) {
				String path = (String)config.getJSONObject(i).get("Object Link");
				String value = (String)config.getJSONObject(i).get("Value");
				ConfigurationItem configurationItem = new ConfigurationItem(path, value);
				configuration.add(configurationItem);
			}
			//Todo: Save in DB
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void getConfiguration() {

	}

}
