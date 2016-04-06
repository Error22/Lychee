package com.error22.lychee.server;

import com.error22.lychee.network.INetworkHandler;
import com.error22.lychee.network.IPacket;
import com.error22.lychee.network.PacketMap;
import com.error22.lychee.network.Ping;
import com.error22.lychee.network.Pong;
import com.error22.lychee.network.NetworkManager;

public class TestNetworkHandler implements INetworkHandler {

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
	public void connected(NetworkManager networkManager) {
		System.out.print(" connected");

	}

}
