package com.p2key.proxy;

import java.io.File;
import java.lang.reflect.Field;

import com.p2key.registry.ServiceRegistry;

public class Proxy {

	public static void findService(String dir) {
		File d = new File(dir);

		File[] fs = d.listFiles();

		for (File file : fs) {
			if (file.isFile()) {
				String path = file.getPath();
				String name = file.getName().replaceAll(".java", "");
				String packName = path.substring(path.indexOf("src\\main\\java") + 14, path.lastIndexOf('\\'));

				String clazzPath = packName + "." + name;

				try {
					Class<?> cls = Class.forName(clazzPath);
					ServiceRegistry.put(cls);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

			} else if (file.isDirectory()) {
				findService(file.getAbsolutePath());
			}
		}
	}

	public static String getResponse(String method, Object o) {
		String result = method + ":";
		try {
			Field[] fields = o.getClass().getDeclaredFields();
			for (Field field : fields) {

				field.setAccessible(true);

				if (field.get(o) instanceof Integer) {
					result += "\n\t" + field.getName() + ":" + field.get(o);
				} else if (field.get(o) instanceof String) {
					result += "\n\t" + field.getName() + ":\"" + field.get(o) + "\"";
				}

			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
