package com.error22.lychee.network;

import java.io.IOException;

public class Handshake implements IPacket {
	private String address, ident;
	private int version, port;

	public Handshake() {
	}

	public Handshake(int version, String ident, String address, int port) {
		this.address = address;
		this.ident = ident;
		this.version = version;
		this.port = port;
	}

	@Override
	public void read(PacketBuffer buffer) throws IOException {
		version = buffer.readInt();
		address = buffer.readString();
		port = buffer.readInt();
		ident = buffer.readString();
	}

	@Override
	public void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(version);
		buffer.writeString(address);
		buffer.writeInt(port);
		buffer.writeString(ident);
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

}
