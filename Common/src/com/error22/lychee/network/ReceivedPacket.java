package com.error22.lychee.network;

public class ReceivedPacket {
	private int id;
	private PacketBuffer buffer;

	public ReceivedPacket(int id, PacketBuffer buffer) {
		this.id = id;
		this.buffer = buffer;
	}

	public int getId() {
		return id;
	}

	public PacketBuffer getBuffer() {
		return buffer;
	}

}
