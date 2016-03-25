package com.error22.lychee.server;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerStream extends OutputStream {
	private final Level logLevel;

	public LoggerStream(Level logLevel) {
		this.logLevel = logLevel;
	}

	@Override
	public void write(byte[] b) throws IOException {
		String string = new String(b);
		if (!string.trim().isEmpty())
			getLog().log(logLevel, string);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		String string = new String(b, off, len);
		if (!string.trim().isEmpty())
			getLog().log(logLevel, string);
	}
	
	@Override
	public void write(int b) throws IOException {
		String string = String.valueOf((char) b);
		if (!string.trim().isEmpty())
			getLog().log(logLevel, string);
	}
	
	private Logger getLog(){
		StackTraceElement[] stes = new Throwable().getStackTrace();
		
		for(int i = 2; i < stes.length; i++){
			String name = stes[i].getClassName();
			if(!name.startsWith("java.") && !name.startsWith("sun")){
				return LogManager.getLogger(name+"::System.out/err");
			}
		}
		return LogManager.getLogger("Unknown::System.out/err");
	}
}