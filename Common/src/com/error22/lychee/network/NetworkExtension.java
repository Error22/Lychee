package com.error22.lychee.network;

import java.util.HashMap;

public enum NetworkExtension {
	Base("base"), Authentication("authentication"), PairedPackets("paired_packets"), ProjectManagement("project_management");

	private String name;

	private NetworkExtension(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	private static HashMap<String, NetworkExtension> extensionMap;

	public static boolean isExtensionKnown(String name) {
		return extensionMap.containsKey(name);
	}

	public static NetworkExtension getExtension(String name) {
		return extensionMap.get(name);
	}

	static {
		extensionMap = new HashMap<String, NetworkExtension>();

		for (NetworkExtension e : values()) {
			extensionMap.put(e.name, e);
		}
	}

}
