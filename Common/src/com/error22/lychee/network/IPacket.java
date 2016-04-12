package com.error22.lychee.network;

import java.io.IOException;
import java.util.EnumSet;

public interface IPacket {

	public void read(INetworkHandler handler, PacketBuffer buffer) throws IOException;
	
	public void write(INetworkHandler handler, PacketBuffer buffer) throws IOException;
	
	public EnumSet<NetworkExtension> getRequiredExtensions();

}
