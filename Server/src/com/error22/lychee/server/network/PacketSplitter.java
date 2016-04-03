package com.error22.lychee.server.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class PacketSplitter extends ByteToMessageDecoder {

	protected void decode(ChannelHandlerContext p_decode_1_, ByteBuf p_decode_2_, List<Object> p_decode_3_) {
		p_decode_2_.markReaderIndex();

		if(p_decode_2_.readableBytes() >= 4){
			int size = p_decode_2_.readInt();

			if (p_decode_2_.readableBytes() >= size) {
				p_decode_3_.add(p_decode_2_.readBytes(size));
				return;
			}
		}
		

		p_decode_2_.resetReaderIndex();

	}
}
