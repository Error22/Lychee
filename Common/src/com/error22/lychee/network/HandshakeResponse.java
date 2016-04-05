package com.error22.lychee.network;

import java.io.IOException;

public class HandshakeResponse implements IPacket {
	private String ident;
	private int version;

	public HandshakeResponse() {
	}

	public HandshakeResponse(int version, String ident) {
		this.ident = ident;
		this.version = version;
	}

	@Override
	public void read(PacketBuffer buffer) throws IOException {
		version = buffer.readInt();
		ident = buffer.readString();
	}

	@Override
	public void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(version);
		buffer.writeString(ident);
	}

	public String getIdent() {
		return ident;
	}

	public int getVersion() {
		return version;
	}

}

