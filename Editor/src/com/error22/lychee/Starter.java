package com.error22.lychee;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.error22.lychee.network.Handshake;
import com.error22.lychee.network.INetworkHandler;
import com.error22.lychee.network.IPacket;
import com.error22.lychee.network.NetworkClient;
import com.error22.lychee.network.NetworkManager;
import com.error22.lychee.network.PacketMap;
import com.error22.lychee.network.Ping;
import com.error22.lychee.network.Pong;
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
				if (!(packet instanceof Ping || packet instanceof Pong))
					System.out.println(" handlePacket " + packet);
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
