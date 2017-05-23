package ch.hsr.smartmanager.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Configuration {
	
	@Id
	private String id;
	
	private String name;
	private List<ConfigurationItem> configurationItems = new ArrayList<>();
	
	public Configuration(String name) {
		this.setName(name);
	}
	
	public void add(ConfigurationItem configurationItem) {
		configurationItems.add(configurationItem);
	}
	public void remove(ConfigurationItem configurationItem) {
		configurationItems.remove(configurationItem);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ConfigurationItem> getConfigurationItems() {
		return configurationItems;
	}

	public void setConfigurationItems(List<ConfigurationItem> configurationItems) {
		this.configurationItems = configurationItems;
	}
	
	

}
