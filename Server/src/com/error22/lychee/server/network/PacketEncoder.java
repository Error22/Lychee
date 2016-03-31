package com.error22.lychee.server.network;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<ISendablePacket> {

	protected void encode(ChannelHandlerContext ctx, ISendablePacket packet, ByteBuf out) throws IOException {
		NetworkClient client = ctx.channel().attr(NetworkClient.attrKey).get();

		PacketMap map = client.getNetworkHandler().getPacketMap();

		if (map.canSendPacket(packet.getClass())) {
			int packetId = map.getPacketId(packet.getClass());

			PacketBuffer buffer = new PacketBuffer(out);
			buffer.writeInt(packetId);
			packet.write(client, buffer);
		} else {
			throw new IOException("Unable to send unmapped packet, " + packet.getClass());
		}
	}

}
