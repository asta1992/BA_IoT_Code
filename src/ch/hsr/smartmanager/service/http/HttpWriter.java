package ch.hsr.smartmanager.service.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ch.hsr.smartmanager.service.IWriter;

public class HttpWriter implements IWriter {
	
	private String requestMethod = "POST";
	private String userAgent = "Mozilla/5.0";
	private String charset = "UTF-8";
	

	@Override
	public String write(String url, String jsonData) {
		try {

			URL obj = new URL(url);
			HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();

			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("User-Agent", userAgent);
			httpURLConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			httpURLConnection.setRequestProperty("Accept-Charset", charset);
			httpURLConnection.setRequestProperty("Content-Type", "application/json");

			// Send post request
			httpURLConnection.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
			wr.writeBytes(jsonData);
			wr.flush();
			wr.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
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
