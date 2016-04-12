package com.error22.lychee.network;

import java.lang.reflect.InvocationTargetException;

import com.error22.lychee.network.packets.EnableExtensions;
import com.error22.lychee.network.packets.Handshake;
import com.error22.lychee.network.packets.HandshakeResponse;
import com.error22.lychee.network.packets.Ping;
import com.error22.lychee.network.packets.Pong;
import com.error22.lychee.network.packets.ProjectList;
import com.error22.lychee.network.packets.RequestProjectList;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class PacketMap {
	private final BiMap<Integer, Class<? extends IPacket>> map;

	public PacketMap() {
		map = HashBiMap.create();
	}

	public PacketMap(PacketMap parent) {
		map = HashBiMap.create(parent.getMap());

	}

	public void registerPacket(int id, Class<? extends IPacket> clazz) {
		map.put(id, clazz);
	}

	public boolean canRecievePacket(int packetId) {
		return map.containsKey(packetId);
	}

	public IPacket constructPacket(int packetId) throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return map.get(packetId).getConstructor().newInstance();
	}

	public boolean canSendPacket(Class<? extends IPacket> class1) {
		return map.inverse().containsKey(class1);
	}

	public int getPacketId(Class<? extends IPacket> class1) {
		return map.inverse().get(class1);
	}

	public BiMap<Integer, Class<? extends IPacket>> getMap() {
		return map;
	}

	private static PacketMap mainPacketMap;
	
	public static PacketMap getMainPacketMap() {
		return mainPacketMap;
	}
	

	static {
		mainPacketMap = new PacketMap();
		//EXT_BASE
		mainPacketMap.registerPacket(-1, Ping.class);
		mainPacketMap.registerPacket(-2, Pong.class);
		mainPacketMap.registerPacket(1, Handshake.class);
		mainPacketMap.registerPacket(2, HandshakeResponse.class);
		mainPacketMap.registerPacket(3, EnableExtensions.class);
		
		//EXT_PROJECT_MANAGEMENT
		mainPacketMap.registerPacket(4, RequestProjectList.class);
		mainPacketMap.registerPacket(5, ProjectList.class);
	}
}
