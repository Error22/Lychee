package com.error22.lychee.server.network;

public interface INetworkHandler {

	public void handlePacket(IReceivablePacket packet);

	public PacketMap getPacketMap();

}
