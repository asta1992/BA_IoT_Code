package ch.hsr.smartmanager.service.applicationservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.leshan.ResponseCode;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import ch.hsr.smartmanager.data.Configuration;
import ch.hsr.smartmanager.data.ConfigurationItem;
import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.repositories.ConfigurationItemRepository;

@Service
public class ConfigurationService {

	@Autowired
	private ConfigurationItemRepository configRepo;
	@Autowired
	private DeviceService deviceService;

	public Configuration saveConfiguration(JSONArray config) {

		try {
			String configurationName = HtmlUtils.htmlEscape(config.getString(0));
			String configurationDescription = HtmlUtils.htmlEscape(config.getString(1));
			Configuration configuration = new Configuration(configurationName, configurationDescription);
			for (int i = 2; i < config.length(); i++) {
				String path = (String) config.getJSONObject(i).get("Object Link");
				String value = HtmlUtils.htmlEscape(config.getJSONObject(i).get("Value").toString());
				ConfigurationItem configurationItem = new ConfigurationItem(path, value);
				configuration.add(configurationItem);
			}
			if (configRepo.existsByName(configurationName)) {
				Configuration updatedConfiguration = configRepo.findByName(configurationName);
				configuration.setId(updatedConfiguration.getId());
				updatedConfiguration = configuration;
				return configRepo.save(updatedConfiguration);
			} else {
				return configRepo.insert(configuration);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Configuration> getAllConfigurations() {
		return configRepo.findAll();
	}

	public Configuration findOne(String id) {
		return configRepo.findOne(id);
	}

	public void deleteConfiguration(String configurationId) {
		Configuration config = configRepo.findOne(configurationId);
		if (config != null) {
			configRepo.delete(config);
		}
	}

	public Map<String, List<Map<String, ResponseCode>>> writeConfigurationToGroup(List<Device> devices,
			String configurationId) {
		Map<String, List<Map<String, ResponseCode>>> responseMap = new HashMap<>();
		for (Device device : devices) {
			responseMap.put(device.getName(), writeConfigurationToDevice(device.getId(), configurationId));
		}
		return responseMap;
	}

	public List<Map<String, ResponseCode>> writeConfigurationToDevice(String deviceId, String configurationId) {
		List<Map<String, ResponseCode>> responseList = new ArrayList<>();
		Configuration configuration = configRepo.findOne(configurationId);

		if (configuration != null) {
			for (ConfigurationItem item : configuration.getConfigurationItems()) {
				responseList.add(deviceService.write(deviceId, getPathPart(item, 1), getPathPart(item, 2),
						getPathPart(item, 3), item.getValue()));
			}
		}
		return responseList;
	}

	private int getPathPart(ConfigurationItem item, int groupNumber) {
		String regex = "([0-9]*)\\/([0-9]*)\\/([0-9]*)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(item.getPath());
		int objectId = 0;
		while (matcher.find()) {
			objectId = Integer.parseInt(matcher.group(groupNumber));
		}
		return objectId;
	}
}
