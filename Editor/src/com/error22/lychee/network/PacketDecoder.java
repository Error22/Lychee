package com.error22.lychee.network;

import java.io.IOException;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class PacketDecoder extends ByteToMessageDecoder {

	protected void decode(ChannelHandlerContext ctx, ByteBuf data, List<Object> results) throws IOException {
		if (data.readableBytes() != 0) {
			System.out.println("--------------------------------------------------------");
			NetworkManager manager = ctx.channel().attr(NetworkManager.attrKey).get();
			PacketBuffer buffer = new PacketBuffer(data);
			int packetId = buffer.readInt();

			PacketMap map = manager.getPacketMap();

			if (map.canRecievePacket(packetId)) {
				try {
					IReceivablePacket packet = map.constructPacket(packetId);

					packet.read(manager, buffer);

					if (buffer.readableBytes() > 0) {
						throw new IOException("Extra data avaliable after reading packet " + packetId + ", "
								+ buffer.readableBytes() + " bytes");
					} else {
						results.add(packet);
					}
				} catch (Exception e) {
					throw new IOException("Failed to read packet " + packetId, e);
				}
			} else {
				throw new IOException("Unknown packet id " + packetId);
			}

			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
		}
	}
}
