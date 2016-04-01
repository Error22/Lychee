package com.error22.lychee.server.network;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.error22.lychee.server.LycheeServer;
import com.error22.lychee.server.Pair;
import com.error22.lychee.server.network.v1.V1EditorHandler;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class NetworkManager {
	private LycheeServer server;
	private NioEventLoopGroup networkGroup;
	private InetAddress host;
	private int port;

	public NetworkManager(LycheeServer server, InetAddress host, int port) {
		this.server = server;
		this.host = host;
		this.port = port;
	}

	public void start() {
		networkGroup = new NioEventLoopGroup(0,
				(new ThreadFactoryBuilder()).setNameFormat("Netty IO #%d").setDaemon(true).build());

		new ServerBootstrap().channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel channel) throws Exception {
				NetworkClient client = new NetworkClient(server);
				channel.attr(NetworkClient.attrKey).set(client);

				ChannelPipeline pipeline = channel.pipeline();
				pipeline.addLast(new ReadTimeoutHandler(30));
				pipeline.addLast("splitter", new PacketSplitter());
				pipeline.addLast("decoder", new PacketDecoder());
				pipeline.addLast("prepender", new PacketPrepender());
				pipeline.addLast("encoder", new PacketEncoder());
				pipeline.addLast("client", client);
			}
		}).group(networkGroup).childOption(ChannelOption.SO_KEEPALIVE, true).localAddress(host, port).bind()
				.syncUninterruptibly();
	}

	private static Map<Pair<Integer, ConnectionType>, Class<? extends INetworkHandler>> networkHandlers;

	public static INetworkHandler createNetworkHandler(int version, ConnectionType type, NetworkClient client)
			throws Exception {
		Pair<Integer, ConnectionType> key = new Pair<Integer, ConnectionType>(version, type);
		if (!networkHandlers.containsKey(key)) {
			throw new Exception("Unsupported version! " + version);
		}
		return networkHandlers.get(key).getConstructor(NetworkClient.class).newInstance(client);
	}

	private static void registerNetworkHandler(int version, ConnectionType type,
			Class<? extends INetworkHandler> handler) {
		networkHandlers.put(new Pair<Integer, ConnectionType>(version, type), handler);
	}

	static {
		networkHandlers = new HashMap<Pair<Integer, ConnectionType>, Class<? extends INetworkHandler>>();
		registerNetworkHandler(1, ConnectionType.Editor, V1EditorHandler.class);

	}

}
