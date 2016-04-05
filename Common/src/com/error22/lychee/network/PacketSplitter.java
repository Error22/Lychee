package com.error22.lychee.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class PacketSplitter extends ByteToMessageDecoder {

	protected void decode(ChannelHandlerContext context, ByteBuf data, List<Object> out) {
		data.markReaderIndex();

		if (data.readableBytes() >= 4) {
			int size = data.readInt();
			if (data.readableBytes() >= size) {
				out.add(data.readBytes(size));
				return;
			}
		}

		data.resetReaderIndex();

	}
}
