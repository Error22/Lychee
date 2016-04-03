package com.error22.lychee;

import com.error22.lychee.network.NetworkManager;
import com.error22.lychee.network.ConnectionType;
import com.error22.lychee.network.packets.Handshake;

public class Starter {
	
	public static void main(String[] args) throws InterruptedException {
		
		NetworkManager networkManager = new NetworkManager();
		networkManager.connect("127.0.0.1", 1234);
		
//		while(!networkManager.isReady()){
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		
		
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}
