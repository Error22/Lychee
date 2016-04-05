package com.error22.lychee.network;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<SentPacket> {

	protected void encode(ChannelHandlerContext ctx, SentPacket packet, ByteBuf out) throws IOException {
		System.out.println("Hello");
		PacketBuffer buffer = new PacketBuffer(out);
		buffer.writeInt(packet.getId());
		packet.getPacket().write(buffer);
	}

}
