package ch.hsr.smartmanager.service.http;

import ch.hsr.smartmanager.service.IConnector;
import ch.hsr.smartmanager.service.IHandler;

public class HttpsConnector implements IConnector {

	@Override
	public IHandler connectToDevice() {
		return new HttpsHandler();
	}

}
