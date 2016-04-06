package com.error22.lychee.network;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class NetworkClient {
	private static NioEventLoopGroup networkGroup = new NioEventLoopGroup(0,
			(new ThreadFactoryBuilder()).setNameFormat("Netty-%d").setDaemon(true).build());
	private NetworkManager manager;
	
	public void connect(String address, int port, INetworkHandler handler) throws InterruptedException {
		manager = new NetworkManager();
		manager.setHandler(handler);
		new Bootstrap().channel(NioSocketChannel.class).group(networkGroup).option(ChannelOption.SO_KEEPALIVE, true)
				.handler(new ChannelInitializer<Channel>() {

					@Override
					protected void initChannel(Channel channel) throws Exception {
						ChannelPipeline pipeline = channel.pipeline();
						pipeline.addLast(new ReadTimeoutHandler(130));
						pipeline.addLast("splitter", new PacketSplitter());
						pipeline.addLast("decoder", new PacketDecoder());
						pipeline.addLast("prepender", new PacketPrepender());
						pipeline.addLast("encoder", new PacketEncoder());
						pipeline.addLast("manager", manager);

					}
				}).connect(address, port).sync();
	}

	public void sendPacket(IPacket packet){
		manager.sendPacket(packet);
	}
	
	public void setNetworkHandler(INetworkHandler handler){
		manager.setHandler(handler);
	}
}
