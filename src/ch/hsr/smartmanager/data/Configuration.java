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
	private List<ConfigurationItem> template = new ArrayList<>();
	
	public Configuration(String name) {
		this.setName(name);
	}
	
	public void add(ConfigurationItem templateObject) {
		template.add(templateObject);
	}
	public void remove(ConfigurationItem templateObject) {
		template.remove(templateObject);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ConfigurationItem> getTemplate() {
		return template;
	}

	public void setTemplate(List<ConfigurationItem> template) {
		this.template = template;
	}
	
	

}
