package com.p2key.registry;

import java.util.HashMap;
import java.util.Map;

import com.p2key.service.P2KeyService;
import com.p2key.service.Service;

public class ServiceRegistry {
	private static Map<String, Class<P2KeyService>> services = new HashMap<String, Class<P2KeyService>>();
	
	public static void put(Class<?> clazz) {
		if (P2KeyService.class.isAssignableFrom(clazz) && !clazz.isInterface()) {
			services.put(clazz.getAnnotation(Service.class).name(), (Class<P2KeyService>) clazz);
		}
	}
	
	public static Class<?> get(String val) {
		return services.get(val);
	}
}
