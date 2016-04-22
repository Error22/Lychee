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
import com.error22.lychee.server.IProject;
import com.error22.lychee.server.LycheeServer;

public class ServerNetworkHandler implements INetworkHandler {
	private static Logger log = LogManager.getLogger();
	private static int serverVersion = 1;
	private static String serverIdent = "lychee-server-1.0-official";
	private NetworkManager manager;
	private ExtensionSet extensions;
	private LycheeServer server;

	@Override
	public void connected(NetworkManager networkManager) {
		System.out.print(" connected");
		server = LycheeServer.INSTANCE;
		manager = networkManager;
		extensions = new ExtensionSet();
		extensions.enable(NetworkExtension.Base);
	}

	@Override
	public void handlePacket(IPacket packet) {
		log.info("handlePacket " + packet);
		if (packet instanceof Ping || packet instanceof Pong) {
		} else if (packet instanceof Handshake) {
			sendPacket(new HandshakeResponse(serverVersion, serverIdent, allSupportedExtensions));
		} else if (packet instanceof EnableExtensions) {
			extensions.enableAll(((EnableExtensions) packet).getExtensionSet());
		} else if (packet instanceof RequestProjectList) {
			handleRequestProjectList((RequestProjectList) packet);
		} else {
			throw new RuntimeException("Unhandled packet! " + packet);
		}
	}

	private void handleRequestProjectList(RequestProjectList packet) {
		UUID pairId = packet.getPairId();

		IProject[] projects = server.getProjects().toArray(new IProject[0]);
		UUID[] ids = new UUID[projects.length];
		String[] names = new String[projects.length];
		String[] types = new String[projects.length];

		for (int i = 0; i < projects.length; i++) {
			ids[i] = projects[i].getId();
			names[i] = projects[i].getName();
			types[i] = projects[i].getType().getNetworkName();
		}

		sendPacket(new ProjectList(true, pairId, ids, names, types));
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
