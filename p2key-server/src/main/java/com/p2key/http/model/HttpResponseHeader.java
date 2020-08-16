package com.p2key.http.model;

import java.util.logging.Level;

import com.p2key.server.log.P2kLogger;

public class HttpResponseHeader extends HttpHeader {
	private String server;
	private HttpStatus status;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getHttpInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getHttpVersion()) //
				.append(" ") //
				.append(this.getStatus().getValue()) //
				.append(" ") //
				.append(this.getStatus().toString());
		long end = System.nanoTime();
		return sb.toString();
	}

	public String getServerInfo() {
		return "Server: " + this.getServer();
	}

	public String getContentTypeInfo() {
		return "Content-type: " + this.getContentType();
	}

	public String getContentLengthInfo() {
		return "Content-length: " + String.valueOf(this.getContentLength());
	}
}
