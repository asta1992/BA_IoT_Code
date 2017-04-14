package ch.hsr.smartmanager.service.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import ch.hsr.smartmanager.data.Credential;
import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.service.IHandler;
import ch.hsr.smartmanager.service.LoginMethod;

public class HttpHandler implements IHandler {
	
	private String userAgent = "Mozilla/5.0";
	private String charset = "UTF-8";
	private CookieManager cookieManager;
	private Device device;
	private HttpURLConnection dataConnection;
	private Map<String, List<String>> headerFields;
	
	@Override
	public void setDevice(Device device) {
		this.device= device;
	}
	
	@Override
	public String read(String url) {
		String response = "";
		switch(loginMethod){
			case NONE:	response = readWithNoAuth(url);
						break;
			case BASIC_AUTH:	response = readWithBasicAuth(url);
						break;
			default:	response = "NO LOGINMETHOD PROVIDED";
						break;
		}
		return response;
	}
	
	private String readWithNoAuth(String url){
		try {
			URL obj = new URL(url);
			dataConnection = (HttpURLConnection) obj.openConnection();
			
			dataConnection.setRequestMethod("GET");

			if (cookieManager != null && cookieManager.getCookieStore().getCookies().size() > 0) {
				dataConnection.setRequestProperty("Cookie",
			    String.join(";", cookieManager.getCookieStore().getCookies().toString()));    
			}
			
			dataConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
			
			System.out.println("RESPONSE CODE: " + dataConnection.getResponseCode());
			if(dataConnection.getResponseCode()!= 401){
				BufferedReader in = new BufferedReader(new InputStreamReader(dataConnection.getInputStream()));
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
	
	private String readWithBasicAuth(String url){
		try {
			URL obj = new URL(url);
			dataConnection = (HttpURLConnection) obj.openConnection();
			if(credential != null){
				String userAndPass = credential.getUsername()+":"+credential.getPassword();
				String basicAuth = "Basic " + new String(Base64.getEncoder().encodeToString(userAndPass.getBytes(StandardCharsets.UTF_8)));
				dataConnection.setRequestProperty("Authorization", basicAuth);
			}
			else{
				return "no credentials provided";
			}
			
			
			dataConnection.setRequestMethod("GET");

			if (cookieManager != null && cookieManager.getCookieStore().getCookies().size() > 0) {
				dataConnection.setRequestProperty("Cookie",
			    String.join(";", cookieManager.getCookieStore().getCookies().toString()));    
			}
			
			dataConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
			
			System.out.println("RESPONSE CODE: " + dataConnection.getResponseCode());
			if(dataConnection.getResponseCode()!= 401){
				BufferedReader in = new BufferedReader(new InputStreamReader(dataConnection.getInputStream()));
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
		private void loadCookies() throws IOException {
			
			cookieManager = new CookieManager();
			headerFields = loginConnection.getHeaderFields();
			System.out.println("RESPONSE CODE2" + loginConnection.getResponseCode());
			List<String> cookiesHeader = headerFields.get("Set-Cookie");
			
			if(cookiesHeader != null) {
				for(String cookie : cookiesHeader){
					System.out.println("Cookie: " + cookie + "--------");
					cookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
				}
			}
		}
		
		//Source: https://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/

				@Override
				public String write(String url, String jsonData) {
					try {
						URL obj = new URL(url);
						dataConnection = (HttpURLConnection) obj.openConnection();

						dataConnection.setRequestMethod("POST");
						
						if (cookieManager != null && cookieManager.getCookieStore().getCookies().size() > 0) {
							dataConnection.setRequestProperty("Cookie",
						    String.join(";", cookieManager.getCookieStore().getCookies().toString()));    
						}
						dataConnection.setRequestProperty("User-Agent", userAgent);
						dataConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
						dataConnection.setRequestProperty("Accept-Charset", charset);
						dataConnection.setRequestProperty("Content-Type", "application/json");

						// Send post request
						dataConnection.setDoOutput(true);
						DataOutputStream wr = new DataOutputStream(dataConnection.getOutputStream());
						wr.writeBytes(jsonData);
						wr.flush();
						wr.close();

						BufferedReader in = new BufferedReader(new InputStreamReader(dataConnection.getInputStream()));
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
