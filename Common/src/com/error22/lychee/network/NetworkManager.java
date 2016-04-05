package com.error22.lychee.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NetworkManager extends SimpleChannelInboundHandler<ReceivedPacket> {
	private Channel channel;
	private INetworkHandler handler;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		channel = ctx.channel();
		channel.config().setAutoRead(true);
		handler.connected(this);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, ReceivedPacket arg1) throws Exception {
//TODO: complete		
		handler.handlePacket(arg1);
	}

	@SafeVarargs
	public final void sendPacket(IPacket packet, GenericFutureListener<? extends Future<? super Void>>... listeners) {
		SentPacket sentPacket = new SentPacket(handler.getPacketMap().getPacketId(packet.getClass()), packet);

		if (channel.eventLoop().inEventLoop()) {
			channel.writeAndFlush(sentPacket).addListeners(listeners)
					.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
		} else {
			channel.eventLoop().execute(new Runnable() {

				@Override
				public void run() {
					channel.writeAndFlush(sentPacket).addListeners(listeners)
							.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
				}
			});
		}
	}

	public void setHandler(INetworkHandler handler) {
		this.handler = handler;
	}

}