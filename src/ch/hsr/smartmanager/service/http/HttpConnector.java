package ch.hsr.smartmanager.service.http;

import ch.hsr.smartmanager.service.IHandler;
import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.service.IConnector;

public class HttpConnector implements IConnector {

	@Override
	public IHandler connectToDevice(Device device) {
		return new HttpHandler(device);
	}

	
}
