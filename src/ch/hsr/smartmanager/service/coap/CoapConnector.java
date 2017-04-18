package ch.hsr.smartmanager.service.coap;

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.service.IConnector;
import ch.hsr.smartmanager.service.IHandler;

public class CoapConnector implements IConnector{

	@Override
	public IHandler connectToDevice(Device device) {
		return new CoapHandlerImpl(device);
	}

}
