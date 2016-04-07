package com.error22.lychee.network;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NetworkManager extends SimpleChannelInboundHandler<ReceivedPacket> {
	private static Logger log = LogManager.getLogger();
	private Channel channel;
	private INetworkHandler handler;
	private BitSet pingIds;
	private HashMap<Integer, Long> pingSendTimes;
	private Timer timer;
	private long ping;

	public NetworkManager(INetworkHandler handler) {
		this.handler = handler;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		pingIds = new BitSet();
		pingSendTimes = new HashMap<Integer, Long>();
		channel = ctx.channel();
		channel.config().setAutoRead(true);
		handler.connected(this);
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				int id = pingIds.nextClearBit(0);
				pingIds.set(id);
				long time = System.currentTimeMillis();
				pingSendTimes.put(id, time);
				sendPacket(new Ping(id, time));
			}
		}, 120000, 120000);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, ReceivedPacket arg1) throws Exception {
		IPacket packet = handler.getPacketMap().constructPacket(arg1.getId());
		packet.read(arg1.getBuffer());

		if (packet instanceof Ping) {
			Ping ping = (Ping) packet;
			sendPacket(new Pong(ping.getId(), ping.getSentTime(), System.currentTimeMillis()));
		} else if (packet instanceof Pong) {
			Pong pong = (Pong) packet;
			if (pingSendTimes.get(pong.getId()) == pong.getSentTime()) {
				ping = pong.getRecievedTime() - pong.getSentTime();
				pingSendTimes.remove(pong.getId());
				pingIds.clear(pong.getId());
			} else {
				log.warn("Incorrect ping/pong sent time, ignoring!");
			}
		}
		handler.handlePacket(packet);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

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

	public long getPing() {
		return ping;
	}


}