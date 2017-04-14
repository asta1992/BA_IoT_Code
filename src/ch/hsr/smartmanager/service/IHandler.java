package ch.hsr.smartmanager.service;

import ch.hsr.smartmanager.data.Credential;
import ch.hsr.smartmanager.data.Device;

public interface IHandler {
	public void setDevice(Device device);
	public String read(String endpoint);
	public String write(String endpoint, String body);

}
