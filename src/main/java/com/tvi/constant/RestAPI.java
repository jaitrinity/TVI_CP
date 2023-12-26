package com.tvi.constant;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Map;

public class RestAPI {
	public static String spInsert(String apiURL, String request){
		String response = null;
		try {
			String URL = apiURL;
			URL obj = new URL(URL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type","application/json");
			con.setRequestProperty("userid", "TVIUser");
			con.setRequestProperty("pwd", "4T!aOGTfnWK_!Vi");
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			//wr.writeBytes(request);
			wr.write(request.toString().getBytes());
			wr.flush();
			wr.close();
			String responseStatus = con.getResponseMessage();
			System.out.println(apiURL+" : "+responseStatus);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer sbresponse = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				sbresponse.append(inputLine);
			}
			in.close();
			response = sbresponse.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public static String rfaiInsert(String apiURL, String request){
		String response = null;
		try {
			String URL = apiURL;
			URL obj = new URL(URL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type","application/json");
			con.setRequestProperty("userid", "TVIUser");
			con.setRequestProperty("pwd", "4T!aOGTfnWK_!Vi");
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			//wr.writeBytes(request);
			wr.write(request.toString().getBytes());
			wr.flush();
			wr.close();
			String responseStatus = con.getResponseMessage();
			System.out.println(apiURL+" : "+responseStatus);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer sbresponse = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				sbresponse.append(inputLine);
			}
			in.close();
			response = sbresponse.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public static String updateSrDetailStatus(String apiURL, String request){
		String response = null;
		try {
			String URL = apiURL;
			URL obj = new URL(URL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type","application/json");
			con.setRequestProperty("userid", "TVIUser");
			con.setRequestProperty("pwd", "TVIPwd");
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			//wr.writeBytes(request);
			wr.write(request.toString().getBytes());
			wr.flush();
			wr.close();
			String responseStatus = con.getResponseMessage();
			System.out.println(apiURL+" : "+responseStatus);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer sbresponse = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				sbresponse.append(inputLine);
			}
			in.close();
			response = sbresponse.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}
