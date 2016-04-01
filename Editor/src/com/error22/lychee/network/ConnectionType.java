package com.error22.lychee.network;

public enum ConnectionType {
	Handshake, Editor;

	public static int getId(ConnectionType type) {
		switch (type) {
		case Editor:
			return 1;
		default:
			throw new RuntimeException("Unknown connection type " + type);
		}
	}

}
