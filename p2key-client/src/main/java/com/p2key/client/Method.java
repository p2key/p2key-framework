package com.p2key.client;

import java.util.HashMap;
import java.util.Map;

public class Method {
	private String name;
	private Map<String, Object> requestParam = new HashMap<String, Object>();
	private Map<String, Object> responseParam = new HashMap<String, Object>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void addRequestParam(String key, Object value) {
		this.requestParam.put(key, value);
	}
	
	public Object getResponseParam(String key) {
		return this.responseParam.get(key);
	}
	
	public String toRequest() {
		String req = this.name + ":\n";
		for (Map.Entry<String, Object> param : this.requestParam.entrySet()) {
			req += "\t"+param.getKey() +":"+param.getValue();
		}
		
		return req;
	}
	
	public void bindResponse(String result) {
		String[] lines = result.split("\n");
		for(int i=1; i<lines.length; i++) {
			String[] param = lines[i].trim().split(":");
			this.responseParam.put(param[0], param[1]);
		}
	}
}
