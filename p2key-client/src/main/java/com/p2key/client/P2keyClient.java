package com.p2key.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class P2keyClient {
	public static Method call(String service, int port, Method method) {
		try {
			URL url = new URL("http://localhost:" + port + "/" + service);
			URLConnection con = url.openConnection();
			HttpURLConnection http = (HttpURLConnection) con;
			http.setRequestMethod("POST");
			http.setRequestProperty("User-Agent", "Java client");
			http.setRequestProperty("Content-Type", "application/xml");
			http.setRequestProperty("Content-Length", String.valueOf(method.toRequest().getBytes().length));
			http.setDoOutput(true);
			http.getOutputStream().write(method.toRequest().getBytes("UTF-8"));
			BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"));
			StringBuilder result = new StringBuilder();
			for (int c; (c = in.read()) >= 0;)
				result.append((char) c);
			
			method.bindResponse(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return method;
	}
}
