package com.error22.lychee.editor.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.error22.lychee.editor.LycheeEditor;
import com.error22.lychee.network.IPacket;
import com.error22.lychee.network.PacketMap;

public class V1ServerConnection implements IServerConnection{
	private static Logger log = LogManager.getLogger();
	private LycheeEditor editor;
	private ClientNetworkHandler networkHandler;

	@Override
	public void init(LycheeEditor editor, ClientNetworkHandler networkHandler) {
		log.info("init");
		this.editor = editor;
		this.networkHandler = networkHandler;
	}

	@Override
	public void handlePacket(IPacket packet) {
		log.info("handlePacket");
	}

	@Override
	public PacketMap getPacketMap() {
		log.info("getPacketMap");
		
		return null;
	}

}
