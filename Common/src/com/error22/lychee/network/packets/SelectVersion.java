package com.error22.lychee.network.packets;

import java.io.IOException;

import com.error22.lychee.network.IPacket;
import com.error22.lychee.network.PacketBuffer;

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
