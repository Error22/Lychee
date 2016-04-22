package com.error22.lychee.network.packets;

import java.io.IOException;
import java.util.EnumSet;
import java.util.UUID;

import com.error22.lychee.network.INetworkHandler;
import com.error22.lychee.network.IPairablePacket;
import com.error22.lychee.network.NetworkExtension;
import com.error22.lychee.network.PacketBuffer;

public class ProjectList implements IPairablePacket {
	private boolean isPaired;
	private UUID pairId;
	private UUID[] ids;
	private String[] names;
	private String[] types;

	public ProjectList() {
	}

	public ProjectList(boolean isPaired, UUID pairId, UUID[] ids, String[] names, String[] types) {
		this.isPaired = isPaired;
		this.pairId = pairId;
		this.ids = ids;
		this.names = names;
		this.types = types;
	}

	@Override
	public void read(INetworkHandler handler, PacketBuffer buffer) throws IOException {
		if (handler.getExtensions().isEnabled(NetworkExtension.PairedPackets)) {
			isPaired = buffer.readBoolean();
			if (isPaired) {
				pairId = buffer.readUUID();
			}
		}
		int count = buffer.readInt();
		ids = new UUID[count];
		names = new String[count];
		types = new String[count];
		for (int i = 0; i < count; i++) {
			ids[i] = buffer.readUUID();
			names[i] = buffer.readString();
			types[i] = buffer.readString();
		}
	}

	@Override
	public void write(INetworkHandler handler, PacketBuffer buffer) throws IOException {
		if (handler.getExtensions().isEnabled(NetworkExtension.PairedPackets)) {
			buffer.writeBoolean(isPaired);
			if (isPaired) {
				buffer.writeUUID(pairId);
			}
		}
		buffer.writeInt(ids.length);
		for (int i = 0; i < ids.length; i++) {
			buffer.writeUUID(ids[i]);
			buffer.writeString(names[i]);
			buffer.writeString(types[i]);
		}
	}

	@Override
	public EnumSet<NetworkExtension> getRequiredExtensions() {
		return EnumSet.of(NetworkExtension.ProjectManagement);
	}

	public UUID[] getIds() {
		return ids;
	}

	public String[] getNames() {
		return names;
	}

	public String[] getTypes() {
		return types;
	}

	@Override
	public boolean isPaired() {
		return isPaired;
	}

	@Override
	public UUID getPairId() {
		return pairId;
	}

}
