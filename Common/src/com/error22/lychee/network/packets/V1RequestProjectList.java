package com.error22.lychee.network.packets;

import java.io.IOException;
import java.util.UUID;

import com.error22.lychee.network.IPacket;
import com.error22.lychee.network.IPairedPacket;
import com.error22.lychee.network.PacketBuffer;

public class V1RequestProjectList implements IPacket, IPairedPacket{
	private UUID id;
	
	@Override
	public void read(PacketBuffer buffer) throws IOException {
		id = UUID.fromString(buffer.readString());
	}

	@Override
	public void write(PacketBuffer buffer) throws IOException {
		buffer.writeString(id.toString());
	}
	
	@Override
	public UUID getPairId() {
		return id;
	}

	@Override
	public boolean isPaired() {
		return true;
	}

	@Override
	public boolean isResponse() {
		return false;
	}

}
