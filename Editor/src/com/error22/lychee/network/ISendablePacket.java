package com.error22.lychee.network;

import java.io.IOException;

public interface ISendablePacket {

	public void write(NetworkManager manager, PacketBuffer buffer) throws IOException;
}
