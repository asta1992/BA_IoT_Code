package ch.hsr.smartmanager.service.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import ch.hsr.smartmanager.service.IConnector;

public class HttpConnector implements IConnector {
	
	//Source: https://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
	
	public void post(){
		
	}

	
	public String write(String url) {
		try {

			URL obj = new URL(url);
			HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();

			// add reuqest header
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
			httpURLConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

			// Send post request
			httpURLConnection.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = httpURLConnection.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
		} catch (IOException e) {

		}
		return "";

	}

	public String read(String url) {
		try {
			URL obj = new URL(url);
			HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();

			httpURLConnection.setRequestMethod("GET");

			httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");

			int responseCode = httpURLConnection.getResponseCode();

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
		return "";
	}

}
