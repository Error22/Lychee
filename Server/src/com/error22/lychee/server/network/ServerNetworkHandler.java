package com.error22.lychee.server.network;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.error22.lychee.network.ExtensionSet;
import com.error22.lychee.network.INetworkHandler;
import com.error22.lychee.network.IPacket;
import com.error22.lychee.network.NetworkExtension;
import com.error22.lychee.network.NetworkManager;
import com.error22.lychee.network.PacketMap;
import com.error22.lychee.network.packets.EnableExtensions;
import com.error22.lychee.network.packets.Handshake;
import com.error22.lychee.network.packets.HandshakeResponse;
import com.error22.lychee.network.packets.Ping;
import com.error22.lychee.network.packets.Pong;
import com.error22.lychee.network.packets.ProjectList;
import com.error22.lychee.network.packets.RequestProjectList;

public class ServerNetworkHandler implements INetworkHandler {
	private static Logger log = LogManager.getLogger();
	private static int serverVersion = 1;
	private static String serverIdent = "lychee-server-1.0-official";
	private NetworkManager manager;
	private ExtensionSet extensions;

	@Override
	public void connected(NetworkManager networkManager) {
		System.out.print(" connected");
		manager = networkManager;
		extensions = new ExtensionSet();
		extensions.enable(NetworkExtension.Base);
	}

	@Override
	public void handlePacket(IPacket packet) {
		log.info("handlePacket "+packet);
		if (packet instanceof Ping || packet instanceof Pong) {
		} else if (packet instanceof Handshake) {
			sendPacket(new HandshakeResponse(serverVersion, serverIdent, allSupportedExtensions));
		} else if (packet instanceof EnableExtensions) {
			extensions.enableAll(((EnableExtensions) packet).getExtensionSet());
		} else if (packet instanceof RequestProjectList) {
			sendPacket(new ProjectList(true, ((RequestProjectList) packet).getPairId(), new UUID[0], new String[0],
					new String[0]));
		} else {
			throw new RuntimeException("Unhandled packet! " + packet);
		}
	}

	public void sendPacket(IPacket packet) {
		if (!extensions.areEnabled(packet.getRequiredExtensions())) {
			throw new RuntimeException(
					packet.getClass() + " requires " + packet.getRequiredExtensions() + " to be enabled!");
		}
		manager.sendPacket(packet);
	}

	@Override
	public PacketMap getPacketMap() {
		return PacketMap.getMainPacketMap();
	}

	@Override
	public ExtensionSet getExtensions() {
		return extensions;
	}

	private static ExtensionSet allSupportedExtensions;

	static {
		allSupportedExtensions = new ExtensionSet();
		allSupportedExtensions.enable(NetworkExtension.Base);
		allSupportedExtensions.enable(NetworkExtension.ProjectManagement);
		allSupportedExtensions.enable(NetworkExtension.PairedPackets);
	}

}
