package com.error22.lychee.server.network;

import java.io.IOException;

public interface IReceivablePacket {

	public void read(NetworkClient client, PacketBuffer buffer) throws IOException;

}
