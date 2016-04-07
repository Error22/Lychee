package com.error22.lychee.server.network;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

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
import com.error22.lychee.server.LycheeServer;
import com.error22.lychee.util.Pair;

public class ServerNetworkHandler implements INetworkHandler {
	private static int serverVersion = 1;
	private static String serverIdent = "lychee-server-1.0-official";
	private NetworkManager manager;
	private int status, targetVersion;
	private IClientHandler handler;

	@Override
	public void connected(NetworkManager networkManager) {
		System.out.print(" connected");
		manager = networkManager;
		status = 1;
	}

	@Override
	public void handlePacket(IPacket packet) {
		if (packet instanceof Ping || packet instanceof Pong) {
		} else if (status == 1 && packet instanceof Handshake) {
			manager.sendPacket(new HandshakeResponse(serverVersion, serverIdent));
			status = 2;
		} else if (status == 2 && packet instanceof SelectVersion) {
			targetVersion = ((SelectVersion) packet).getVersion();
			status = 3;
		} else if (status == 3 && packet instanceof EnterMode) {
			ClientType type = ((EnterMode) packet).getType();
			handler = getClientHandler(targetVersion, type);
			status = 4;
			handler.init(LycheeServer.INSTANCE, this);
		}else if(status == 4){
			handler.handlePacket(packet);
		} else {
			throw new RuntimeException("Unhandled packet!");
		}
	}

	public void sendPacket(IPacket packet) {
		manager.sendPacket(packet);
	}

	@Override
	public PacketMap getPacketMap() {
		return status == 4 ? handler.getPacketMap() : PacketMap.getHandshakePacketMap();
	}

	private static HashMap<Pair<Integer, ClientType>, Class<? extends IClientHandler>> clientHandlers;

	private static void registerHandler(int version, ClientType type, Class<? extends IClientHandler> handler) {
		clientHandlers.put(new Pair<Integer, ClientType>(version, type), handler);
	}

	private static IClientHandler getClientHandler(int version, ClientType type) {
		try {
			return clientHandlers.get(new Pair<Integer, ClientType>(version, type)).getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("Unsupported client handler, " + version + " + " + type, e);
		}
	}

	static {
		clientHandlers = new HashMap<Pair<Integer, ClientType>, Class<? extends IClientHandler>>();

		registerHandler(1, ClientType.Editor, V1Editor.class);
	}

}
