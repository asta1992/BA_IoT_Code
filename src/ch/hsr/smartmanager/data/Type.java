package ch.hsr.smartmanager.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="type")
public class Type {
	

	@Id
	public String id;
	
	private String protocolType;
	private String authType;
	
	public Type() {}
	
	public Type(String protocolType, String authType) {
		this.protocolType = protocolType;
		this.authType = authType;
	}

	public String getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	@Override
	public String toString() {
		return "Type [id=" + id + ", protocolType=" + protocolType + ", authType=" + authType + "]";
	}
	
	
	
	

}
