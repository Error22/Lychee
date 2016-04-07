package com.error22.lychee.server.network;

import com.error22.lychee.network.ClientType;
import com.error22.lychee.network.IPacket;
import com.error22.lychee.network.PacketMap;
import com.error22.lychee.server.LycheeServer;

public class V1Editor implements IClientHandler, IEditor {
	
	@Override
	public void init(LycheeServer instance, ServerNetworkHandler networkHandler) {
		
	}
	
	@Override
	public void handlePacket(IPacket packet) {
		
	}

	@Override
	public PacketMap getPacketMap() {
		return PacketMap.getPacketMap(1, ClientType.Editor);
	}

}
