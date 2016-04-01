package com.error22.lychee.network.packets;

import java.io.IOException;

import com.error22.lychee.network.ConnectionType;
import com.error22.lychee.network.ISendablePacket;
import com.error22.lychee.network.NetworkManager;
import com.error22.lychee.network.PacketBuffer;

public class Handshake implements ISendablePacket {
	private String address, ident;
	private int version, port;
	private ConnectionType type;

	public Handshake(String address, String ident, int version, int port, ConnectionType type) {
		this.address = address;
		this.ident = ident;
		this.version = version;
		this.port = port;
		this.type = type;
	}

	@Override
	public void write(NetworkManager manager, PacketBuffer buffer) throws IOException {
		buffer.writeInt(version);
		buffer.writeString(address);
		buffer.writeInt(port);
		buffer.writeString(ident);
		buffer.writeInt(ConnectionType.getId(type));
	}

}
