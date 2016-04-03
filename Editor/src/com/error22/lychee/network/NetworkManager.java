package com.error22.lychee.network;

import com.error22.lychee.network.packets.Handshake;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NetworkManager extends SimpleChannelInboundHandler<IReceivablePacket> {
	public static final AttributeKey<NetworkManager> attrKey = AttributeKey.valueOf("network_manager");
	private Channel channel;
	private String address;
	private int port;
	private NioEventLoopGroup networkGroup;
	private ConnectionStatus status;

	public void connect(String address, int port) throws InterruptedException {
		this.address = address;
		this.port = port;
		networkGroup = new NioEventLoopGroup(0,
				(new ThreadFactoryBuilder()).setNameFormat("Netty IO #%d").setDaemon(true).build());
		new Bootstrap().channel(NioSocketChannel.class).group(networkGroup).option(ChannelOption.SO_KEEPALIVE, true)
				.handler(new ChannelInitializer<Channel>() {

					@Override
					protected void initChannel(Channel channel) throws Exception {
						channel.attr(attrKey).set(NetworkManager.this);

						ChannelPipeline pipeline = channel.pipeline();
						pipeline.addLast(new ReadTimeoutHandler(30));
						pipeline.addLast("splitter", new PacketSplitter());
						pipeline.addLast("decoder", new PacketDecoder());
						pipeline.addLast("prepender", new PacketPrepender());
						pipeline.addLast("encoder", new PacketEncoder());
						pipeline.addLast("server", NetworkManager.this);

					}
				}).connect(address, port).sync();
		System.out.println("Abc");
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		channel = ctx.channel();
		System.out.println("Active!");
		channel.config().setAutoRead(true);
		status = ConnectionStatus.Handshake;
		sendPacket(new Handshake("127.0.0.1", "testClient", 1, 1234, ConnectionType.Editor));
//		sendPacket(new Handshake("127.0.0.1", "abc", 1, 1023, ConnectionType.Editor));
//		channel.
	}

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, IReceivablePacket arg1) throws Exception {
		System.out.println("read "+arg1);
		// TODO Auto-generated method stub
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
	
	public PacketMap getPacketMap() {
		switch (status) {
		case Handshake:
			return handshakePacketMap;
		case Authenticating:
			return mainPacketMap;
		default:
			throw new RuntimeException("Unhandled status " + status);
		}
	}

	private static final PacketMap handshakePacketMap, mainPacketMap;

	static {
		handshakePacketMap = new PacketMap();
		handshakePacketMap.registerSendablePacket(0, Handshake.class);

		mainPacketMap = new PacketMap();
	}

}
