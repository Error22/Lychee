package com.error22.lychee.editor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.error22.lychee.editor.network.ClientNetworkHandler;
import com.error22.lychee.network.NetworkClient;
import com.error22.lychee.util.Util;

public class Starter {
	private static Logger log = LogManager.getLogger();

	public static void main(String[] args) throws InterruptedException {
		Util.tieSystemOutAndErrToLog();
		log.info("Starting!");

		NetworkClient client = new NetworkClient();
		client.connect("127.0.0.1", 1234, new ClientNetworkHandler());

		
		//TODO: Replace
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
