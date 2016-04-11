package com.error22.lychee.editor.network;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.error22.lychee.editor.LycheeEditor;
import com.error22.lychee.network.INetworkHandler;
import com.error22.lychee.network.IPacket;
import com.error22.lychee.network.NetworkManager;
import com.error22.lychee.network.PacketMap;
import com.error22.lychee.network.packets.ClientType;
import com.error22.lychee.network.packets.EnterMode;
import com.error22.lychee.network.packets.Handshake;
import com.error22.lychee.network.packets.HandshakeResponse;
import com.error22.lychee.network.packets.Ping;
import com.error22.lychee.network.packets.Pong;
import com.error22.lychee.network.packets.SelectVersion;

public class ClientNetworkHandler implements INetworkHandler {
	private static int clientVersion = 1;
	private static String clientIdent = "lychee-editor-1.0-official";
	private static Logger log = LogManager.getLogger();
	private NetworkManager networkManager;
	private LycheeEditor editor;
	private String address;
	private int port;
	private boolean routed;

	public ClientNetworkHandler(LycheeEditor editor, String address, int port) {
		this.editor = editor;
		this.address = address;
		this.port = port;
	}

	@Override
	public void connected(NetworkManager manager) {
		networkManager = manager;
		editor.setConnectionStatus(ConnectionStatus.Handshaking);
		log.info(" connected");
		sendPacket(new Handshake(clientVersion, clientIdent, address, port));
	}

	@Override
	public void handlePacket(IPacket packet) {
		if (packet instanceof Ping || packet instanceof Pong) {
		} else if (!routed && packet instanceof HandshakeResponse) {
			HandshakeResponse response = (HandshakeResponse) packet;
			log.info("Server ident: " + response.getIdent());

			int targetVersion = response.getVersion() > clientVersion ? clientVersion : response.getVersion();
			sendPacket(new SelectVersion(targetVersion));
			sendPacket(new EnterMode(ClientType.Editor));
			editor.setConnection(getServerConnectionHandler(targetVersion));
			editor.getConnection().init(editor, this);
			editor.setConnectionStatus(ConnectionStatus.Connected);
			routed = true;
		} else if (routed) {
			editor.getConnection().handlePacket(packet);
		} else {
			throw new RuntimeException("Unhandled packet!");
		}

	}

	@Override
	public PacketMap getPacketMap() {
		return routed ? editor.getConnection().getPacketMap() : PacketMap.getHandshakePacketMap();
	}

	public void sendPacket(IPacket packet) {
		networkManager.sendPacket(packet);
	}

	private static HashMap<Integer, Class<? extends IServerConnection>> versionMappings;

	private static void registerHandler(int version, Class<? extends IServerConnection> handler) {
		versionMappings.put(version, handler);
	}

	private static IServerConnection getServerConnectionHandler(int version) {
		try {
			return versionMappings.get(version).getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("Unsupported server connection handler, " + version);
		}
	}

	static {
		versionMappings = new HashMap<Integer, Class<? extends IServerConnection>>();

		registerHandler(1, V1ServerConnection.class);
	}

}
