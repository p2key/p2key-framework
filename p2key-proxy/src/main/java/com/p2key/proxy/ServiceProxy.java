package com.p2key.proxy;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.p2key.registry.ServiceRegistry;

public class ServiceProxy {
	private String method;
	private Object[] obj;
	private Class<?> params[];

	public Object callService(String serviceName, String requestBody) {
		String result = null;
		try {
			mapParams(requestBody);

			Proxy.findService("src/main/java");
			
			Object _instance = ServiceRegistry.get(serviceName).getDeclaredConstructor().newInstance();
			Method m = ServiceRegistry.get(serviceName).getDeclaredMethod(method, params);
			Object o = m.invoke(_instance, obj);

			result = Proxy.getResponse(method, o);
			
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	public static void listOfPackage(String directoryName, Set<String> pack) {
		File directory = new File(directoryName);

		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				String path = file.getPath();
				String packName = path.substring(path.indexOf("src\\main\\java") + 14, path.lastIndexOf('\\'));
				pack.add(packName.replace('\\', '.'));
			} else if (file.isDirectory()) {
				listOfPackage(file.getAbsolutePath(), pack);
			}
		}
	}

	private void mapParams(String requestBody) {
		List<Object> objs = new ArrayList<Object>();
		String[] parts = requestBody.split("\n");
		method = parts[0].replace("\n", "").substring(0, parts[0].replace("\n", "").length() - 1);
		for (int i = 1; i < parts.length; i++) {
			String val = parts[i].trim().split(":")[1].trim();
			if (val.startsWith("\"") && val.endsWith("\"")) {
				objs.add(val.replace("\"", ""));
			} else if (isNumeric(val)) {
				objs.add(Integer.valueOf(val));
			}else {
				objs.add(val);
			}
		}
		obj = objs.toArray();
		params = new Class[obj.length];
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] instanceof Integer) {
				params[i] = Integer.TYPE;
			} else if (obj[i] instanceof String) {
				params[i] = String.class;
			}
		}
	}

	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
