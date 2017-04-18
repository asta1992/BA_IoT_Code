package ch.hsr.smartmanager.service.coap;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.springframework.stereotype.Service;

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.service.IHandler;

@Service
public class CoapHandlerImpl implements IHandler {

	private CoapClient coapClient;
	private String response;
	
	public CoapHandlerImpl(Device device) {
		this.coapClient = new CoapClient(device.getEndpoint());
	}
	
	public String discovery(String path) {
		return coapClient.discover(path).toString();
	}


	@Override
	public String read() {
		
		coapClient.get(new CoapHandler() {
			
			@Override
			public void onLoad(CoapResponse res) {
				response = res.getResponseText();
			}
			
			@Override
			public void onError() {
				response = "Error";
			}
		});
		
		return response;
		
	}
	
	public String query(String string) {
		return "";
	}
	
	@Override
	public String write(String body) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDevice(Device device) {
	}
	
	public void setCoapClient(CoapClient coapClient) {
		this.coapClient = coapClient;
	}
	
	



}
