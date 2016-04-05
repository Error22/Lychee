package com.error22.lychee.network;

public enum ClientType {
	Editor;

	public static int getId(ClientType type) {
		switch (type) {
		case Editor:
			return 1;
		default:
			throw new RuntimeException("Unknown connection type " + type);
		}
	}
	
	public static ClientType getType(int id) {
		switch (id) {
		case 1:
			return ClientType.Editor;
		default:
			throw new RuntimeException("Unknown connection id " + id);
		}
	}

}
