package com.error22.lychee.network;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import com.error22.lychee.util.Pair;
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

	private static PacketMap handshakePacketMap;
	private static HashMap<Pair<Integer, ClientType>, PacketMap> packetMaps;

	private static PacketMap createPacketMap(int parent, int version, ClientType type) {
		PacketMap map = parent > 0 ? new PacketMap(packetMaps.get(parent)) : new PacketMap();
		packetMaps.put(new Pair<Integer, ClientType>(version, type), map);
		return map;
	}

	public static PacketMap getHandshakePacketMap() {
		return handshakePacketMap;
	}

	static {
		packetMaps = new HashMap<Pair<Integer, ClientType>, PacketMap>();
		handshakePacketMap = new PacketMap();
		handshakePacketMap.registerPacket(1, Handshake.class);
		handshakePacketMap.registerPacket(2, HandshakeResponse.class);

		PacketMap v1Editor = createPacketMap(-1, 1, ClientType.Editor);

	}
}
