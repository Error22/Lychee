package com.error22.lychee.network;

import java.io.IOException;

public interface IReceivablePacket {

	public void read(NetworkManager manager, PacketBuffer buffer) throws IOException;

}
