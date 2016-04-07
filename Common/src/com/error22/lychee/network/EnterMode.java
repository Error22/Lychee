package com.error22.lychee.network;

import java.io.IOException;

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
