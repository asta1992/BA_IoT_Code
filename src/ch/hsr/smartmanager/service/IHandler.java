package ch.hsr.smartmanager.service;

import ch.hsr.smartmanager.data.Credential;

public interface IHandler {
	public boolean login(String endpoint, Credential credential) throws Exception;
	public String read(String endpoint);
	public String write(String endpoint, String body);

}
