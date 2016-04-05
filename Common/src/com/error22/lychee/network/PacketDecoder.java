package com.error22.lychee.network;

import java.io.IOException;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class PacketDecoder extends ByteToMessageDecoder {

	protected void decode(ChannelHandlerContext ctx, ByteBuf data, List<Object> results) throws IOException {
		if (data.readableBytes() != 0) {
			results.add(new ReceivedPacket(data.readInt(), new PacketBuffer(data)));
		}
	}
}
