package com.p2key.http.processor;

import java.util.StringTokenizer;

import com.p2key.http.model.HttpMethod;
import com.p2key.http.model.HttpRequestHeader;

public class HttpHeaderParser {
	public static void parse(HttpRequestHeader requestHeader, String line) {
		String delimiter = ": ";
		StringTokenizer st = new StringTokenizer(line, delimiter);
		String key = st.nextToken();
		String val = st.nextToken();
		if(HttpHeaderKey.CONTENT_TYPE.getValue().equals(key)) {
			requestHeader.setContentType(val);
		}else if (HttpHeaderKey.CONTENT_LENGTH.getValue().equals(key)) {
			requestHeader.setContentLength(Integer.valueOf(val));
		}
	}
	
	public static void parseL(HttpRequestHeader requestHeader, String line) {
		String delimiter = " ";
		StringTokenizer st = new StringTokenizer(line, delimiter);
		String method = st.nextToken();
		String service = st.nextToken();
		String version = st.nextToken();
		requestHeader.setMethod(HttpMethod.valueOf(method));
		requestHeader.setService(service.replace("/",""));
		requestHeader.setHttpVersion(version);
	}
	
}