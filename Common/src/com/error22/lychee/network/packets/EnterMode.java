package com.error22.lychee.network.packets;

import java.io.IOException;

import com.error22.lychee.network.IPacket;
import com.error22.lychee.network.PacketBuffer;

public class EnterMode implements IPacket {
	private ClientType type;

	public EnterMode() {
	}

	public EnterMode(ClientType type) {
		this.type = type;
	}

	@Override
	public void read(PacketBuffer buffer) throws IOException {
		type = ClientType.getType(buffer.readInt());
	}

	@Override
	public void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(ClientType.getId(type));
	}

	public ClientType getType() {
		return type;
	}
	
}
