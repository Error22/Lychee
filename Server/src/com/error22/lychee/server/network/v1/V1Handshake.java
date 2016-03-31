package com.error22.lychee.server.network.v1;

import java.io.IOException;

import com.error22.lychee.server.network.ConnectionType;
import com.error22.lychee.server.network.IReceivablePacket;
import com.error22.lychee.server.network.NetworkClient;
import com.error22.lychee.server.network.PacketBuffer;

public class V1Handshake implements IReceivablePacket {
	private String address, ident;
	private int version, port;
	private ConnectionType type;

	@Override
	public void read(NetworkClient client, PacketBuffer buffer) throws IOException {
		version = buffer.readInt();
		address = buffer.readString();
		port = buffer.readInt();
		ident = buffer.readString();
		type = ConnectionType.getConnectionType(buffer.readInt());
	}

	public String getAddress() {
		return address;
	}

	public String getIdent() {
		return ident;
	}

	public int getVersion() {
		return version;
	}

	public int getPort() {
		return port;
	}

	public ConnectionType getType() {
		return type;
	}

}
