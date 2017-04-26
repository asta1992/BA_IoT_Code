package ch.hsr.smartmanager.data;

import org.eclipse.leshan.server.registration.Registration;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "devices")
public class Device {

	@Id
	private String id;

	private String name;
	private Registration registration;
	private AuthType authType;
	private String endpoint;
	private String username;
	private String password;
	
	public Device(String name, Registration registration, AuthType authType, String endpoint, String username,
			String password) {
		this.name = name;
		this.registration = registration;
		this.authType = authType;
		this.endpoint = endpoint;
		this.username = username;
		this.password = password;
	}
	
	public Device(String name, Registration registration, ProtocolType protocolType, AuthType authType, String endpoint, String username,
			String password) {
		this.name = name;
		this.registration = registration;
		this.authType = authType;
		this.endpoint = endpoint;
		this.username = username;
		this.password = password;
	}

	public Device() {
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

	public AuthType getAuthType() {
		return authType;
	}

	public void setAuthType(AuthType authType) {
		this.authType = authType;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEntpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Registration getRegistration() {
		return registration;
	}

	public void setRegistration(Registration registration) {
		this.registration = registration;
	}

	@Override
	public String toString() {
		return "Device [id=" + id + ", name=" + name + ", registration=" + registration + ", authType=" + authType
				+ ", endpoint=" + endpoint + ", username=" + username + ", password=" + password + "]";
	}

	public boolean isNew() {
		return (this.id == null);
	}

}
