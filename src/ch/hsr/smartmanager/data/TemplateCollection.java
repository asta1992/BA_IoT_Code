package ch.hsr.smartmanager.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TemplateCollection {
	
	@Id
	private String id;
	
	private String name;
	private List<Template> template = new ArrayList<>();
	
	public TemplateCollection(String name) {
		this.setName(name);
	}
	
	public void add(Template templateObject) {
		template.add(templateObject);
	}
	public void remove(Template templateObject) {
		template.remove(templateObject);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Template> getTemplate() {
		return template;
	}

	public void setTemplate(List<Template> template) {
		this.template = template;
	}
	
	

}
