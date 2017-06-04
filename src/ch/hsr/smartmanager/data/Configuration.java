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
	private String description;
	private List<ConfigurationItem> configurationItems = new ArrayList<>();
	
	public Configuration(String name, String description) {
		this.setName(name);
		this.setDescription(description);
	}
	
	public void add(ConfigurationItem configurationItem) {
		configurationItems.add(configurationItem);
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ConfigurationItem> getConfigurationItems() {
		return configurationItems;
	}

	public void setConfigurationItems(List<ConfigurationItem> configurationItems) {
		this.configurationItems = configurationItems;
	}
}
