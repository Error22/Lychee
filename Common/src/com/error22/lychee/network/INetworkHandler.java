package com.error22.lychee.network;

public interface INetworkHandler {

	public void connected(NetworkManager networkManager);

	public void handlePacket(IPacket packet);

	public PacketMap getPacketMap();
	
	public ExtensionSet getExtensions();

}
