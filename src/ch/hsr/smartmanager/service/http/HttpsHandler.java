package ch.hsr.smartmanager.service.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import ch.hsr.smartmanager.data.Credential;
import ch.hsr.smartmanager.service.IHandler;

public class HttpsHandler implements IHandler {
	
	private String userAgent = "Mozilla/5.0";
	private String charset = "UTF-8";
	private CookieManager cookieManager;
	private HttpsURLConnection loginConnection;
	private HttpsURLConnection dataConnection;
	private Map<String, List<String>> headerFields;
	
	
	@Override
	public String read(String url) {
		try {
			URL obj = new URL(url);
			dataConnection = (HttpsURLConnection) obj.openConnection();
			
			dataConnection.setRequestMethod("GET");

			if (cookieManager != null && cookieManager.getCookieStore().getCookies().size() > 0) {
				dataConnection.setRequestProperty("Cookie",
			    String.join(";", cookieManager.getCookieStore().getCookies().toString()));    
			}
			
			dataConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(dataConnection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//Source: https://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/

		@Override
		public String write(String url, String jsonData) {
			try {
				URL obj = new URL(url);
				dataConnection = (HttpsURLConnection) obj.openConnection();

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

		@Override
		public boolean login(String endpoint, Credential credential) {
			try {
				URL obj = new URL(endpoint);
				loginConnection = (HttpsURLConnection) obj.openConnection();
				String userAndPass = credential.getUsername() + ":" + credential.getPassword();
				String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userAndPass.getBytes()));
				loginConnection.setRequestProperty("Authorization", basicAuth);
				loadCookies();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		//source: http://stackoverflow.com/questions/16150089/how-to-handle-cookies-in-httpurlconnection-using-cookiemanager
		private void loadCookies() {
			
			cookieManager = new CookieManager();
			headerFields = loginConnection.getHeaderFields();
			List<String> cookiesHeader = headerFields.get("Set-Cookie");
			
			if(cookiesHeader != null) {
				for(String cookie : cookiesHeader){
					cookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
				}
			}
		}

}
