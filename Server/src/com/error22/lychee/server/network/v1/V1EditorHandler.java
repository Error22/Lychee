package com.error22.lychee.server.network.v1;

import com.error22.lychee.server.network.INetworkHandler;
import com.error22.lychee.server.network.IReceivablePacket;
import com.error22.lychee.server.network.NetworkClient;
import com.error22.lychee.server.network.PacketMap;

public class V1EditorHandler implements INetworkHandler {
	private NetworkClient networkClient;
	
	public V1EditorHandler(NetworkClient networkClient) {
		this.networkClient = networkClient;
	}
	
	
	@Override
	public void handlePacket(IReceivablePacket packet) {
		System.out.println("handlePacket "+packet);

	}

	@Override
	public PacketMap getPacketMap() {
		// TODO Auto-generated method stub
		return null;
	}

}
