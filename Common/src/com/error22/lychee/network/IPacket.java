package com.error22.lychee.network;

import java.io.IOException;

public interface IPacket {

	public void read(PacketBuffer buffer) throws IOException;
	
	public void write(PacketBuffer buffer) throws IOException;

}
