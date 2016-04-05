package com.error22.lychee;

import com.error22.lychee.network.Handshake;
import com.error22.lychee.network.INetworkHandler;
import com.error22.lychee.network.IPacket;
import com.error22.lychee.network.NetworkClient;
import com.error22.lychee.network.NetworkManager;
import com.error22.lychee.network.PacketMap;

public class Starter {
	
	public static void main(String[] args) throws InterruptedException {
		
		NetworkClient client = new NetworkClient();
		client.connect("127.0.0.1", 1234, new INetworkHandler() {
			
			@Override
			public void handlePacket(IPacket packet) {
				System.out.println(" handlePacket "+packet);
			}
			
			@Override
			public PacketMap getPacketMap() {
				System.out.println(" getPacketMap");
				return PacketMap.getHandshakePacketMap();
			}
			
			@Override
			public void connected(NetworkManager manager) {
				System.out.println(" connected");
				client.sendPacket(new Handshake(5, "test123", "127.0.0.1", 1234));
			}
		});
		
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
