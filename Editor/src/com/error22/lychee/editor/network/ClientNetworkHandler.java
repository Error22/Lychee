package com.error22.lychee.editor.network;

import com.error22.lychee.network.INetworkHandler;
import com.error22.lychee.network.IPacket;
import com.error22.lychee.network.NetworkManager;
import com.error22.lychee.network.PacketMap;
import com.error22.lychee.network.packets.Handshake;
import com.error22.lychee.network.packets.HandshakeResponse;
import com.error22.lychee.network.packets.Ping;
import com.error22.lychee.network.packets.Pong;

public class ClientNetworkHandler implements INetworkHandler{
	private NetworkManager networkManager;
	
	@Override
	public void connected(NetworkManager manager) {
		networkManager = manager;
		System.out.println(" connected");
		sendPacket(new Handshake(5, "test123", "127.0.0.1", 1234));
	}
	
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

	public void sendPacket(IPacket packet){
		networkManager.sendPacket(packet);
	}

}
