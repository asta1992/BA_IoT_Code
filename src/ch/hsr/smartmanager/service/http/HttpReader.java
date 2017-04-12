package ch.hsr.smartmanager.service.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ch.hsr.smartmanager.service.IReader;

public class HttpReader implements IReader {

	@Override
	public String read(String url) {
		try {
			URL obj = new URL(url);
			HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();

			httpURLConnection.setRequestMethod("GET");

			httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");

			BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
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
}
