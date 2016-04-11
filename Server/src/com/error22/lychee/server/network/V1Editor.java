package com.error22.lychee.server.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.error22.lychee.network.IPacket;
import com.error22.lychee.network.PacketMap;
import com.error22.lychee.network.packets.ClientType;
import com.error22.lychee.server.LycheeServer;

public class V1Editor implements IClientHandler, IEditor {
	private static Logger log = LogManager.getLogger();
	
	@Override
	public void init(LycheeServer instance, ServerNetworkHandler networkHandler) {
		log.info("init");
	}
	
	@Override
	public void handlePacket(IPacket packet) {
		log.info("handlePacket");
	}

	@Override
	public PacketMap getPacketMap() {
		return PacketMap.getPacketMap(1, ClientType.Editor);
	}

}
