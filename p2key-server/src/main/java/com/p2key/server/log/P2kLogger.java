package com.p2key.server.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class P2kLogger extends Logger {

	public P2kLogger(String name, String resourceBundleName) {
		super(name, resourceBundleName);
	}
	
	 public static P2kLogger getP2kLogger(String name) {
		 P2kLogger logger = new P2kLogger(name ,null);
		 logger.getLogger(name);
			/*
		    try { 
		    	//TODO: create log file per 1 hour again
		    
		    	FileHandler fh = new FileHandler("log/p2kserver"+System.nanoTime()+".log");  
		        logger.addHandler(fh);
		        SimpleFormatter formatter = new SimpleFormatter();  
		        fh.setFormatter(formatter); 
		        
		    } catch (SecurityException e) {  
		        e.printStackTrace();  
		    } catch (IOException e) {  
		        e.printStackTrace();  
		    } 
		    */
		    return logger;
	 }
	
	 @Override
	 public void log(Level level, String msg)  {
		 if(Level.CONFIG.equals(level)) {
			 System.out.println(msg);
		 } else if(Level.WARNING.equals(level)) {
			 System.err.println(msg);
		 }
		 super.log(level, msg);
	 }
	 
}