package com.error22.lychee.server.network;

import java.io.IOException;

public interface ISendablePacket {

	public void write(NetworkClient client, PacketBuffer buffer) throws IOException;
}
