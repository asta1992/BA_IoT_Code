package ch.hsr.smartmanager.service.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import ch.hsr.smartmanager.service.IConnector;
import ch.hsr.smartmanager.service.IHandler;

public class HttpConnector implements IConnector {

	@Override
	public IHandler connectToDevice() {
		return new HttpHandler();
	}

	
	
	
	

}
