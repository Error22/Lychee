package com.error22.lychee.network.packets;

import java.io.IOException;
import java.util.EnumSet;

import com.error22.lychee.network.ExtensionSet;
import com.error22.lychee.network.INetworkHandler;
import com.error22.lychee.network.IPacket;
import com.error22.lychee.network.NetworkExtension;
import com.error22.lychee.network.PacketBuffer;

public class EnableExtensions implements IPacket {
	private ExtensionSet extensionSet;

	public EnableExtensions() {
	}

	public EnableExtensions(ExtensionSet extensionSet) {
		this.extensionSet = extensionSet;
	}

	@Override
	public void read(INetworkHandler handler, PacketBuffer buffer) throws IOException {
		extensionSet = new ExtensionSet();
		int count = buffer.readInt();
		for (int i = 0; i < count; i++) {
			String name = buffer.readString();
			System.out.println(" "+name);
			if (NetworkExtension.isExtensionKnown(name)){
				extensionSet.enable(NetworkExtension.getExtension(name));
			}
		}
		System.out.println("left "+buffer.readableBytes());
	}

	@Override
	public void write(INetworkHandler handler, PacketBuffer buffer) throws IOException {
		NetworkExtension[] extensions = extensionSet.getAllEnabled();
		buffer.writeInt(extensions.length);
		for (NetworkExtension e : extensions) {
			System.out.println(" "+e.getName());
			buffer.writeString(e.getName());
		}
	}

	public ExtensionSet getExtensionSet() {
		return extensionSet;
	}

	@Override
	public EnumSet<NetworkExtension> getRequiredExtensions() {
		return EnumSet.of(NetworkExtension.Base);
	}

}
