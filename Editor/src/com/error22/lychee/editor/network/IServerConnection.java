package com.error22.lychee.editor.network;

import com.error22.lychee.editor.LycheeEditor;
import com.error22.lychee.network.IPacket;
import com.error22.lychee.network.PacketMap;

public interface IServerConnection {

	public void init(LycheeEditor instance, ClientNetworkHandler networkHandler);

	public void handlePacket(IPacket packet);

	public PacketMap getPacketMap();

}
