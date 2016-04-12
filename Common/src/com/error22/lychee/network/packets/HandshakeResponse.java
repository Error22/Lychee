package com.error22.lychee.network.packets;

import java.io.IOException;
import java.util.EnumSet;

import com.error22.lychee.network.ExtensionSet;
import com.error22.lychee.network.INetworkHandler;
import com.error22.lychee.network.IPacket;
import com.error22.lychee.network.NetworkExtension;
import com.error22.lychee.network.PacketBuffer;

public class HandshakeResponse implements IPacket {
	private String ident;
	private int version;
	private ExtensionSet extensionSet;

	public HandshakeResponse() {
	}

	public HandshakeResponse(int version, String ident, ExtensionSet extensionSet) {
		this.ident = ident;
		this.version = version;
		this.extensionSet = extensionSet;
	}

	@Override
	public void read(INetworkHandler handler, PacketBuffer buffer) throws IOException {
		version = buffer.readInt();
		ident = buffer.readString();
		extensionSet = new ExtensionSet();
		int count = buffer.readInt();
		for (int i = 0; i < count; i++) {
			String name = buffer.readString();
			if (NetworkExtension.isExtensionKnown(name)){
				extensionSet.enable(NetworkExtension.getExtension(name));
			}
		}
	}

	@Override
	public void write(INetworkHandler handler, PacketBuffer buffer) throws IOException {
		buffer.writeInt(version);
		buffer.writeString(ident);
		NetworkExtension[] extensions = extensionSet.getAllEnabled();
		buffer.writeInt(extensions.length);
		for (NetworkExtension e : extensions) {
			buffer.writeString(e.getName());
		}
	}

	public String getIdent() {
		return ident;
	}

	public int getVersion() {
		return version;
	}

	public ExtensionSet getExtensionSet() {
		return extensionSet;
	}
	
	@Override
	public EnumSet<NetworkExtension> getRequiredExtensions() {
		return EnumSet.of(NetworkExtension.Base);
	}


}
