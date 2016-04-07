package com.error22.lychee.server.network;

import com.error22.lychee.network.IPacket;
import com.error22.lychee.network.PacketMap;
import com.error22.lychee.server.LycheeServer;

public interface IClientHandler {

	public void init(LycheeServer instance, ServerNetworkHandler networkHandler);
	
	public void handlePacket(IPacket packet);

	public PacketMap getPacketMap();

}
