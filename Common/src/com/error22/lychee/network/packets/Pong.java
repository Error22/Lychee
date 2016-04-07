package com.error22.lychee.network.packets;

import java.io.IOException;

import com.error22.lychee.network.IPacket;
import com.error22.lychee.network.PacketBuffer;

public class Pong implements IPacket {
	private int id;
	private long sentTime, recievedTime;

	public Pong() {
	}

	public Pong(int id, long sentTime, long recievedTime) {
		this.id = id;
		this.sentTime = sentTime;
		this.recievedTime = recievedTime;
	}

	@Override
	public void read(PacketBuffer buffer) throws IOException {
		id = buffer.readInt();
		sentTime = buffer.readLong();
		recievedTime = buffer.readLong();
	}

	@Override
	public void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(id);
		buffer.writeLong(sentTime);
		buffer.writeLong(recievedTime);
	}

	public int getId() {
		return id;
	}

	public long getSentTime() {
		return sentTime;
	}

	public long getRecievedTime() {
		return recievedTime;
	}

}
