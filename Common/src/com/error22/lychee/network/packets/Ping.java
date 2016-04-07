package com.error22.lychee.network.packets;

import java.io.IOException;

import com.error22.lychee.network.IPacket;
import com.error22.lychee.network.PacketBuffer;

public class Ping implements IPacket {
	private int id;
	private long sentTime;

	public Ping() {
	}
	
	public Ping(int id, long sentTime) {
		this.id = id;
		this.sentTime = sentTime;
	}

	@Override
	public void read(PacketBuffer buffer) throws IOException {
		id = buffer.readInt();
		sentTime = buffer.readLong();
	}

	@Override
	public void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(id);
		buffer.writeLong(sentTime);
	}

	public int getId() {
		return id;
	}

	public long getSentTime() {
		return sentTime;
	}

}
