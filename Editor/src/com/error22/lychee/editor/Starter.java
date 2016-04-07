package com.error22.lychee.editor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.error22.lychee.network.INetworkHandler;
import com.error22.lychee.network.IPacket;
import com.error22.lychee.network.NetworkClient;
import com.error22.lychee.network.NetworkManager;
import com.error22.lychee.network.PacketMap;
import com.error22.lychee.network.packets.Handshake;
import com.error22.lychee.network.packets.HandshakeResponse;
import com.error22.lychee.network.packets.Ping;
import com.error22.lychee.network.packets.Pong;
import com.error22.lychee.util.Util;

public class Starter {
	private static Logger log = LogManager.getLogger();

	public static void main(String[] args) throws InterruptedException {
		Util.tieSystemOutAndErrToLog();
		log.info("Starting!");

		NetworkClient client = new NetworkClient();
		client.connect("127.0.0.1", 1234, new INetworkHandler() {

			@Override
			public void handlePacket(IPacket packet) {
				if (packet instanceof Ping || packet instanceof Pong) {
				}else if(packet instanceof HandshakeResponse){
					
				}else{
					throw new RuntimeException("Unhandled packet!");
				}
				
			}

			@Override
			public PacketMap getPacketMap() {
				return PacketMap.getHandshakePacketMap();
			}

			@Override
			public void connected(NetworkManager manager) {
				System.out.println(" connected");
				client.sendPacket(new Handshake(5, "test123", "127.0.0.1", 1234));
			}
		});

		// while(!networkManager.isReady()){
		// try {
		// Thread.sleep(10);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }

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
