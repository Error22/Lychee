package com.error22.lychee.server.network;

import java.net.SocketAddress;

import com.error22.lychee.server.LycheeServer;
import com.error22.lychee.server.network.v1.V1Handshake;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NetworkClient extends SimpleChannelInboundHandler<IReceivablePacket> {
	public static final AttributeKey<NetworkClient> attrKey = AttributeKey.valueOf("network_client");
	private Channel channel;
	private SocketAddress address;
	private LycheeServer server;
	private INetworkHandler networkHandler;
	private String ident;
	private int version;
	private ConnectionType type;

	public NetworkClient(LycheeServer server) {
		this.server = server;
		type = ConnectionType.Handshake;
		version = -1;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		channel = ctx.channel();
		address = channel.remoteAddress();
		System.out.println("Active! " + address);
		channel.config().setAutoRead(true);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, IReceivablePacket arg1) throws Exception {
		System.out.print("read " + arg1);
		if (type == ConnectionType.Handshake) {
			if (arg1 instanceof V1Handshake) {
				V1Handshake handshake = (V1Handshake) arg1;
				version = handshake.getVersion();
				type = handshake.getType();
				ident = handshake.getIdent();

				networkHandler = NetworkManager.createNetworkHandler(version, type, this);
				networkHandler.handlePacket(arg1);
			} else {
				throw new RuntimeException("We should not be handling " + arg1);
			}
		} else {
			networkHandler.handlePacket(arg1);
		}

	}

	@SafeVarargs
	public final void sendPacket(ISendablePacket packet,
			GenericFutureListener<? extends Future<? super Void>>... listeners) {
		System.out.println("Open? " + channel.isOpen());

		if (channel.eventLoop().inEventLoop()) {
			channel.writeAndFlush(packet).addListeners(listeners)
					.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
		} else {
			channel.eventLoop().execute(new Runnable() {

				@Override
				public void run() {
					channel.writeAndFlush(packet).addListeners(listeners)
							.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
				}
			});
		}
	}

	public Channel getChannel() {
		return channel;
	}

	public SocketAddress getAddress() {
		return address;
	}

	public INetworkHandler getNetworkHandler() {
		return networkHandler;
	}

	public PacketMap getPacketMap() {
		return type == ConnectionType.Handshake ? handshakePacketMap : networkHandler.getPacketMap();
	}

	public LycheeServer getServer() {
		return server;
	}

	public int getVersion() {
		return version;
	}

	public ConnectionType getType() {
		return type;
	}

	public static AttributeKey<NetworkClient> getAttrkey() {
		return attrKey;
	}

	public String getIdent() {
		return ident;
	}

	private static final PacketMap handshakePacketMap;

	static {
		handshakePacketMap = new PacketMap();
		handshakePacketMap.registerReceivablePacket(0, V1Handshake.class);
	}

}
