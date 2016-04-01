package com.error22.lychee.network;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class PacketMap {
	private final Map<Integer, Class<? extends IReceivablePacket>> receivablePacketMap;
	private final Map<Class<? extends ISendablePacket>, Integer> sendablePacketMap;

	public PacketMap() {
		receivablePacketMap = new HashMap<Integer, Class<? extends IReceivablePacket>>();
		sendablePacketMap = new HashMap<Class<? extends ISendablePacket>, Integer>();
	}

	public PacketMap(PacketMap parent) {
		receivablePacketMap = new HashMap<Integer, Class<? extends IReceivablePacket>>(parent.getReceivablePacketMap());
		sendablePacketMap = new HashMap<Class<? extends ISendablePacket>, Integer>(parent.getSendablePacketMap());
	}

	public void registerReceivablePacket(int id, Class<? extends IReceivablePacket> clazz) {
		receivablePacketMap.put(id, clazz);
	}

	public void registerSendablePacket(int id, Class<? extends ISendablePacket> clazz) {
		sendablePacketMap.put(clazz, id);
	}

	public boolean canRecievePacket(int packetId) {
		return receivablePacketMap.containsKey(packetId);
	}

	public IReceivablePacket constructPacket(int packetId) throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return receivablePacketMap.get(packetId).getConstructor().newInstance();
	}

	public boolean canSendPacket(Class<? extends ISendablePacket> class1) {
		return sendablePacketMap.containsKey(class1);
	}

	public int getPacketId(Class<? extends ISendablePacket> class1) {
		return sendablePacketMap.get(class1);
	}

	public Map<Integer, Class<? extends IReceivablePacket>> getReceivablePacketMap() {
		return receivablePacketMap;
	}

	public Map<Class<? extends ISendablePacket>, Integer> getSendablePacketMap() {
		return sendablePacketMap;
	}

}
