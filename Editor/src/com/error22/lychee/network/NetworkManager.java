package com.error22.lychee.network;

import com.error22.lychee.network.packets.Handshake;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.AttributeKey;

public class NetworkManager extends SimpleChannelInboundHandler<IReceivablePacket> {
	public static final AttributeKey<NetworkManager> attrKey = AttributeKey.valueOf("network_manager");
	private Channel channel;
	private String address;
	private int port;
	private NioEventLoopGroup networkGroup;
	private ConnectionStatus status;

	public void connect(String address, int port) {
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
				}).connect(address, port).syncUninterruptibly();

	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		channel = ctx.channel();
		channel.config().setAutoRead(true);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, IReceivablePacket arg1) throws Exception {
		// TODO Auto-generated method stub

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
