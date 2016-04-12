package com.error22.lychee.network.packets;

import java.io.IOException;
import java.util.EnumSet;
import java.util.UUID;

import com.error22.lychee.network.INetworkHandler;
import com.error22.lychee.network.IPairablePacket;
import com.error22.lychee.network.NetworkExtension;
import com.error22.lychee.network.PacketBuffer;

public class RequestProjectList implements IPairablePacket {
	private boolean isPaired;
	private UUID pairId;

	public RequestProjectList() {
	}

	public RequestProjectList(boolean isPaired, UUID pairId) {
		this.isPaired = isPaired;
		this.pairId = pairId;
	}

	@Override
	public void read(INetworkHandler handler, PacketBuffer buffer) throws IOException {
		if (handler.getExtensions().isEnabled(NetworkExtension.PairedPackets)){
			isPaired = buffer.readBoolean();
			if(isPaired){
				pairId = buffer.readUUID();
			}
		}
	}

	@Override
	public void write(INetworkHandler handler, PacketBuffer buffer) throws IOException {
		if(handler.getExtensions().isEnabled(NetworkExtension.PairedPackets)){
			buffer.writeBoolean(isPaired);
			if(isPaired){
				buffer.writeUUID(pairId);
			}
		}
	}

	@Override
	public EnumSet<NetworkExtension> getRequiredExtensions() {
		return EnumSet.of(NetworkExtension.ProjectManagement);
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
