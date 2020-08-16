package com.p2key.server;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

import com.p2key.http.map.HttpHeaderMapper;
import com.p2key.http.model.HttpMethod;
import com.p2key.http.model.HttpRequestHeader;
import com.p2key.http.model.HttpResponseHeader;
import com.p2key.http.processor.HttpHeaderParser;
import com.p2key.server.log.P2kLogger;

public class P2kServer  implements Runnable {
	private Socket connect;
	private static int port = 8089;
	private static P2kLogger logger = P2kLogger.getP2kLogger("p2kLogger");
	
	public P2kServer(Socket c) {
		connect = c;
	}
	public static void main(String[] args) {
		try {
			ServerSocket serverConnect = new ServerSocket(port);
			logger.log(Level.CONFIG, "Server started.\nListening for connections on port : " + serverConnect.getLocalPort() + " ...\n");
			while (true) {
				P2kServer server = new P2kServer(serverConnect.accept());
				Thread thread = new Thread(server);
				thread.start();
			}
		} catch (IOException e) {
			logger.log(Level.WARNING, "Server Connection error : " + e.getMessage());
		}
	}

	@Override
	public void run() {
		
		BufferedReader in = null;
		PrintWriter out = null;
		BufferedOutputStream dataOut = null;
		HttpRequestHeader reqHeader = new HttpRequestHeader();
		long start = System.nanoTime();
		try {
			
			logger.log(Level.INFO, "->Thread["+Thread.currentThread().getId() +"]: Connection opened.\n");
			in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			out = new PrintWriter(connect.getOutputStream());
			dataOut = new BufferedOutputStream(connect.getOutputStream());
			reqHeader = getHttpHeader(in);
			if (reqHeader.getMethod() != null) {
				sendHttpHeader(out, HttpHeaderMapper.get());
			}
		} catch (IOException ioe) {
			System.err.println("Server error : " + ioe);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			try {
				logger.log(Level.INFO, "Connection Time: "+(System.nanoTime() -start)+" ns");
				in.close();
				out.close();
				dataOut.close();
				connect.close(); 
			} catch (Exception e) {
				logger.log(Level.WARNING, "Error closing stream : " + e.getMessage());
			}

			logger.log(Level.INFO, "Thread["+Thread.currentThread().getId() +"]: Connection closed.\n");

		}
		
	}
	
	private HttpRequestHeader getHttpHeader(BufferedReader in) {
		HttpRequestHeader requestHeader = new HttpRequestHeader();
		try {
			String line = in.readLine();
			if (line != null) {
				HttpHeaderParser.parseL(requestHeader, line);
				while ((line = in.readLine()) != null && !line.equals("")) {
					HttpHeaderParser.parse(requestHeader, line);
				}
			}
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}

		return requestHeader;
	}

	private void sendHttpHeader(PrintWriter out, HttpResponseHeader responseHeader) {
		out.println(responseHeader.getHttpInfo());
		out.println(responseHeader.getServerInfo());
		out.println(responseHeader.getContentTypeInfo());
		out.println(responseHeader.getContentLengthInfo());
		out.println();
		out.flush();
	}

}
