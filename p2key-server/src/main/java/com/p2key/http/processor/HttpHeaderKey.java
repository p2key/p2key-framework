package com.p2key.http.processor;

public enum HttpHeaderKey {
	CONTENT_TYPE("Content-Type"), CONTENT_LENGTH("Content-Length");

	private final String value;

	HttpHeaderKey(String value) {
		this.value = value;
	}
	
	public String getValue() {
        return value;
    }
}
