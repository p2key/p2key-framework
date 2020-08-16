package com.p2key.http.model;

public enum HttpStatus {
	OK("200");

	private final String value;

	HttpStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
        return value;
    }

}
