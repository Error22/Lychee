package com.error22.lychee.editor.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.error22.lychee.editor.LycheeEditor;
import com.error22.lychee.network.ExtensionSet;
import com.error22.lychee.network.INetworkHandler;
import com.error22.lychee.network.IPacket;
import com.error22.lychee.network.IPairablePacket;
import com.error22.lychee.network.NetworkExtension;
import com.error22.lychee.network.NetworkManager;
import com.error22.lychee.network.PacketMap;
import com.error22.lychee.network.packets.EnableExtensions;
import com.error22.lychee.network.packets.Handshake;
import com.error22.lychee.network.packets.HandshakeResponse;
import com.error22.lychee.network.packets.Ping;
import com.error22.lychee.network.packets.Pong;
import com.error22.lychee.util.ThreadPauser;
import com.error22.lychee.util.ThreadPauser.EmptyObj;

public class ClientNetworkHandler implements INetworkHandler {
	private static int clientVersion = 1;
	private static String clientIdent = "lychee-editor-1.0-official";
	private static Logger log = LogManager.getLogger();
	private NetworkManager networkManager;
	private LycheeEditor editor;
	private String address, serverIdent;
	private int port;
	private ExtensionSet extensions;
	private HashMap<UUID, ThreadPauser> threadPausers;
	private List<ThreadPauser> awaitingConnected;
	private boolean fullyConnected;

	public ClientNetworkHandler(LycheeEditor editor, String address, int port) {
		this.editor = editor;
		this.address = address;
		this.port = port;
		threadPausers = new HashMap<UUID, ThreadPauser>();
		awaitingConnected = new ArrayList<ThreadPauser>();
		fullyConnected = false;
		extensions = new ExtensionSet();
		extensions.enable(NetworkExtension.Base);
	}

	@Override
	public void connected(NetworkManager manager) {
		networkManager = manager;
		editor.setConnectionStatus(ConnectionStatus.Handshaking);
		log.info(" connected");
		sendPacket(new Handshake(clientVersion, clientIdent, address, port, allSupportedExtensions));
	}

	@Override
	public void handlePacket(IPacket packet) {
		log.info("handlePacket " + packet);
		if (packet instanceof Ping || packet instanceof Pong) {
		} else if (packet instanceof HandshakeResponse) {
			HandshakeResponse response = (HandshakeResponse) packet;
			serverIdent = response.getIdent();
			log.info("Server ident: " + response.getIdent());

			if (response.getVersion() != clientVersion) {
				log.error("Server is not running the same base version as client!");
				throw new RuntimeException("ADD DISCONNECT");
			}

			ExtensionSet wantedExtensions = new ExtensionSet();
			NetworkExtension[] serverExtensions = response.getExtensionSet().getAllEnabled();
			for (NetworkExtension e : serverExtensions) {
				if (allSupportedExtensions.isEnabled(e)) {
					wantedExtensions.enable(e);
				}
			}

			sendPacket(new EnableExtensions(wantedExtensions));
			extensions.enableAll(wantedExtensions);

			editor.setConnectionStatus(ConnectionStatus.Connected);
			
			for(ThreadPauser pauser : awaitingConnected){
				pauser.poke(new EmptyObj());
			}

		} else if (extensions.isEnabled(NetworkExtension.PairedPackets) && packet instanceof IPairablePacket
				&& ((IPairablePacket) packet).isPaired()) {
			threadPausers.get(((IPairablePacket) packet).getPairId()).poke(packet);
		} else {
			throw new RuntimeException("Unhandled packet!");
		}

	}
	
	public void awaitFullConnected(){
		if(!fullyConnected){
			ThreadPauser pauser = new ThreadPauser(true, false);
			awaitingConnected.add(pauser);
			pauser.awaitPoke();
		}
	}

	@Override
	public PacketMap getPacketMap() {
		return PacketMap.getMainPacketMap();
	}

	public String getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public String getServerIdent() {
		return serverIdent;
	}
	
	@Override
	public ExtensionSet getExtensions() {
		return extensions;
	}

	public void sendPacket(IPacket packet) {
		if (!extensions.areEnabled(packet.getRequiredExtensions())) {
			throw new RuntimeException(
					packet.getClass() + " requires " + packet.getRequiredExtensions() + " to be enabled!");
		}
		networkManager.sendPacket(packet);
	}

	public IPairablePacket sendPairedPacket(IPairablePacket packet) {
		ThreadPauser pauser = new ThreadPauser(true, false);
		threadPausers.put(packet.getPairId(), pauser);
		sendPacket(packet);
		return (IPairablePacket) pauser.awaitPoke();
	}

	public static ExtensionSet allSupportedExtensions;

	static {
		allSupportedExtensions = new ExtensionSet();
		allSupportedExtensions.enable(NetworkExtension.Base);
		allSupportedExtensions.enable(NetworkExtension.ProjectManagement);
		allSupportedExtensions.enable(NetworkExtension.PairedPackets);
	}

}
