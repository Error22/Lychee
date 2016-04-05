package com.error22.lychee.server;

import com.error22.lychee.network.INetworkHandler;
import com.error22.lychee.network.IPacket;
import com.error22.lychee.network.PacketMap;
import com.error22.lychee.network.NetworkManager;

public class TestNetworkHandler implements INetworkHandler{

	@Override
	public void handlePacket(IPacket packet) {
		System.out.println(" handlePacket "+packet );
	}

	@Override
	public PacketMap getPacketMap() {
		System.out.println(" getPacketMap");
		return PacketMap.getHandshakePacketMap();
	}

	@Override
	public void connected(NetworkManager networkManager) {
		System.out.print(" connected");
		
	}

}
