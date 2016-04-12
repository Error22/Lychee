package com.error22.lychee.network;

public class SentPacket {
	private INetworkHandler handler;
	private int id;
	private IPacket packet;

	public SentPacket(INetworkHandler handler, int id, IPacket packet) {
		this.handler = handler;
		this.id = id;
		this.packet = packet;
	}

	public INetworkHandler getHandler() {
		return handler;
	}

	public int getId() {
		return id;
	}

	public IPacket getPacket() {
		return packet;
	}

}
