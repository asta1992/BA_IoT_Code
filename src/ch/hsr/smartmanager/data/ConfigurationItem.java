package ch.hsr.smartmanager.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigurationItem {
	
	private String path;
	private String value;

	
	public ConfigurationItem(String path, String value) {
		this.path = path;
		this.value = value;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public int getPathPart(int groupNumber) {
		String regex = "([0-9]*)\\/([0-9]*)\\/([0-9]*)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(path);
		int objectId = 0;
		while (matcher.find()) {
			objectId =  Integer.parseInt(matcher.group(groupNumber));
		}
		return objectId;
	}
	
	@Override
	public String toString() {
		return "ConfigurationItem [path=" + path + ", value=" + value + "]";
	}
	
	

}
