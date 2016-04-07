package com.error22.lychee.network;

import java.io.IOException;

public class SelectVersion implements IPacket{
	private int version;

	public SelectVersion() {
	}
	
	public SelectVersion(int version) {
		this.version = version;
	}
	
	@Override
	public void read(PacketBuffer buffer) throws IOException {
		version = buffer.readInt();
	}
	
	@Override
	public void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(version);
	}
	
	public int getVersion() {
		return version;
	}
	
}
