package ch.hsr.smartmanager.service;

import ch.hsr.smartmanager.data.Device;

public interface IConnector {
	public IHandler connectToDevice(Device device);
	
}
