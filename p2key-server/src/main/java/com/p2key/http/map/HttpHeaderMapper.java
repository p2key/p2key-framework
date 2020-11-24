package com.p2key.http.map;

import com.p2key.http.model.HttpResponseHeader;
import com.p2key.http.model.HttpStatus;

public class HttpHeaderMapper {
	public static HttpResponseHeader get(int length) {
		HttpResponseHeader responseHeader = new HttpResponseHeader();
		responseHeader.setServer("P2K Server 1.0");
		responseHeader.setStatus(HttpStatus.OK);
		responseHeader.setContentType("aplication/text");
		responseHeader.setContentLength(length);
		responseHeader.setHttpVersion("HTTP/1.1");
		return responseHeader;
	}
}
