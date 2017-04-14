package ch.hsr.smartmanager.service.http;

import ch.hsr.smartmanager.service.IHandler;
import ch.hsr.smartmanager.service.IConnector;

public class HttpConnector implements IConnector {

	@Override
	public IHandler connectToDevice() {
		return new HttpHandler();
	}

	
}
