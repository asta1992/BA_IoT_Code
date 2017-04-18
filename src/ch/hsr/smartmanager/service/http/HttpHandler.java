package ch.hsr.smartmanager.service.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
//import java.util.List;
//import java.util.Map;


import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.service.IHandler;

public class HttpHandler implements IHandler {
	
	private String userAgent = "Mozilla/5.0";
	private String charset = "UTF-8";
	private CookieManager cookieManager;
	private Device device;
	private HttpURLConnection connection;
	//private Map<String, List<String>> headerFields;
	
	public HttpHandler(Device device){
		this.device = device;
	}
	
	public void setDevice(Device device) {
		this.device= device;
	}
	
	@Override
	public String read() {
		String response = "";
		switch(device.getAuthType()){
			case NONE:	response = readWithNoAuth();
						break;
			case BASIC_AUTH:	response = readWithBasicAuth();
						break;
			default:	response = "NO LOGINMETHOD PROVIDED";
						break;
		}
		return response;
	}
	
	private String readWithNoAuth(){
		try {
			URL obj = new URL(device.getEndpoint());
			connection = (HttpURLConnection) obj.openConnection();
			
			connection.setRequestMethod("GET");

			if (cookieManager != null && cookieManager.getCookieStore().getCookies().size() > 0) {
				connection.setRequestProperty("Cookie",
			    String.join(";", cookieManager.getCookieStore().getCookies().toString()));    
			}
			
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");
			
			System.out.println("RESPONSE CODE: " + connection.getResponseCode());
			if(connection.getResponseCode()!= 401){
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				return response.toString();
			}
			return "not authorized for that resource";
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String readWithBasicAuth(){
		try {
			URL obj = new URL(device.getEndpoint());
			connection = (HttpURLConnection) obj.openConnection();
			if(device.getUsername() != null && device.getPassword() != null){
				String userAndPass = device.getUsername() + ":" + device.getPassword();
				String basicAuth = "Basic " + new String(Base64.getEncoder().encodeToString(userAndPass.getBytes(StandardCharsets.UTF_8)));
				connection.setRequestProperty("Authorization", basicAuth);
			}
			else{
				return "no credentials provided";
			}
			
			
			connection.setRequestMethod("GET");

			if (cookieManager != null && cookieManager.getCookieStore().getCookies().size() > 0) {
				connection.setRequestProperty("Cookie",
			    String.join(";", cookieManager.getCookieStore().getCookies().toString()));    
			}
			
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");
			
			System.out.println("RESPONSE CODE: " + connection.getResponseCode());
			if(connection.getResponseCode()!= 401){
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				return response.toString();
			}
			return "not authorized for that resource";
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}

//		@Override
//		public boolean login(String endpoint, Credential credential) {
//			try {
//				URL obj = new URL(endpoint);
//				loginConnection = (HttpURLConnection) obj.openConnection();
//				String userAndPass = credential.getUsername()+":"+credential.getPassword();
//				String basicAuth = "Basic " + new String(Base64.getEncoder().encodeToString(userAndPass.getBytes(StandardCharsets.UTF_8)));
//				loginConnection.setRequestProperty("Authorization", basicAuth);
//				loadCookies();
//				return true;
//			} catch (IOException e) {
//				e.printStackTrace();
//				return false;
//			}
//		}
		//source: http://stackoverflow.com/questions/16150089/how-to-handle-cookies-in-httpurlconnection-using-cookiemanager
//		private void loadCookies() throws IOException {
//			
//			cookieManager = new CookieManager();
//			headerFields = loginConnection.getHeaderFields();
//			System.out.println("RESPONSE CODE2" + loginConnection.getResponseCode());
//			List<String> cookiesHeader = headerFields.get("Set-Cookie");
//			
//			if(cookiesHeader != null) {
//				for(String cookie : cookiesHeader){
//					System.out.println("Cookie: " + cookie + "--------");
//					cookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
//				}
//			}
//		}
		
		//Source: https://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/

				@Override
				public String write(String jsonData) {
					try {
						URL obj = new URL(device.getEndpoint());
						connection = (HttpURLConnection) obj.openConnection();

						connection.setRequestMethod("POST");
						
						if (cookieManager != null && cookieManager.getCookieStore().getCookies().size() > 0) {
							connection.setRequestProperty("Cookie",
						    String.join(";", cookieManager.getCookieStore().getCookies().toString()));    
						}
						connection.setRequestProperty("User-Agent", userAgent);
						connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
						connection.setRequestProperty("Accept-Charset", charset);
						connection.setRequestProperty("Content-Type", "application/json");

						// Send post request
						connection.setDoOutput(true);
						DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
						wr.writeBytes(jsonData);
						wr.flush();
						wr.close();

						BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
						String inputLine;
						StringBuffer response = new StringBuffer();

						while ((inputLine = in.readLine()) != null) {
							response.append(inputLine);
						}
						in.close();
						return response.toString();
					} catch (IOException e) {

					}
					return null;
				}
}
