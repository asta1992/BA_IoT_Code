package ch.hsr.smartmanager.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="credentials")
public class Credential {
	
	@Id
	public String id;
	
	public String username;
	public String password;
	
	public Credential() {}

	public Credential(String username, String password) {
		this.username = username;
		this.password = password;
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

	@Override
	public String toString() {
		return "Credential [id=" + id + ", username=" + username + ", password=" + password + "]";
	};
	
	
	
	
	

}
