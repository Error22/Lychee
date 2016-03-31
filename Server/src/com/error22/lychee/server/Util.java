package com.error22.lychee.server;

import java.io.PrintStream;
import java.util.List;

import org.apache.logging.log4j.Level;

public class Util {

	public static <T> T single(List<T> list) {
		if (list.size() == 1) {
			return list.get(0);
		} else if (list.size() == 0) {
			throw new RuntimeException("No results found!");
		} else {
			throw new RuntimeException("More results returned than one!");
		}
	}

	public static void tieSystemOutAndErrToLog() {
		System.setOut(new PrintStream(new LoggerStream(Level.INFO)));
		System.setErr(new PrintStream(new LoggerStream(Level.ERROR)));
	}

}
