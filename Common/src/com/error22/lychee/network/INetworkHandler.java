package com.error22.lychee.network;

public interface INetworkHandler {

	public void handlePacket(IPacket packet);

	public PacketMap getPacketMap();

	public void connected(NetworkManager networkManager);

}
