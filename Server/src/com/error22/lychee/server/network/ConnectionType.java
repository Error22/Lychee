package com.error22.lychee.server.network;

public enum ConnectionType {
	Handshake, Editor;

	public static ConnectionType getConnectionType(int id) {
		switch (id) {
		case 1:
			return ConnectionType.Editor;
		default:
			throw new RuntimeException("Unknown connection id " + id);
		}
	}

}
