package com.error22.lychee.network;

public class SentPacket {
	private int id;
	private IPacket packet;

	public SentPacket(int id, IPacket packet) {
		this.id = id;
		this.packet = packet;
	}

	public int getId() {
		return id;
	}

	public IPacket getPacket() {
		return packet;
	}

}
