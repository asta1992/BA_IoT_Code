package ch.hsr.smartmanager.service;

import ch.hsr.smartmanager.data.Device;

public interface IHandler {
	public void setDevice(Device device);
	public String read();
	public String write(String body);

}
