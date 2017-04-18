package ch.hsr.smartmanager.service.coap;

import org.eclipse.californium.core.CoapClient;

import ch.hsr.smartmanager.data.Credential;
import ch.hsr.smartmanager.service.IHandler;

public class CoapHandler implements IHandler {

	
	private CoapClient client = new CoapClient("coap://127.0.0.1:38430/3");

	
	@Override
	public boolean login(String endpoint,  credential) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String read(String endpoint) {
		System.out.println(client.get().getResponseText());
		return "";
	}

	@Override
	public String write(String endpoint, String body) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	
	public String observer() {
		CoapObserveRelation relation = client.observe(new CoapHandler() { 
			
			
			@Override
			public void onLoad(CoapResponse arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				
			}
		})
		*/
	
	

}
