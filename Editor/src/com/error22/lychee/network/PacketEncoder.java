package com.error22.lychee.network;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<ISendablePacket> {

	protected void encode(ChannelHandlerContext ctx, ISendablePacket packet, ByteBuf out) throws IOException {
		NetworkManager manager = ctx.channel().attr(NetworkManager.attrKey).get();

		PacketMap map = manager.getPacketMap();

		if (map.canSendPacket(packet.getClass())) {
			int packetId = map.getPacketId(packet.getClass());

			PacketBuffer buffer = new PacketBuffer(out);
			buffer.writeInt(packetId);
			packet.write(manager, buffer);
		} else {
			throw new IOException("Unable to send unmapped packet, " + packet.getClass());
		}
	}

}
