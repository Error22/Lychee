//package com.error22.lychee.server.network;
//
//import com.error22.lychee.network.IPacket;
//import com.error22.lychee.network.PacketMap;
//import com.error22.lychee.network.Handshake;
//import com.error22.lychee.network.INetworkHandler;
//
//public class V1EditorHandler implements INetworkHandler {
//	private NetworkClient networkClient;
//	
//	public V1EditorHandler(NetworkClient networkClient) {
//		this.networkClient = networkClient;
//	}
//	
//	
//	@Override
//	public void handlePacket(IPacket packet) {
//		if(packet instanceof Handshake){
//			Handshake handshake = (Handshake) packet;
//			
//			
//			
//			
//		}
//		
//		
//		
//		
//		System.out.println("handlePacket "+packet);
//
//	}
//
//	@Override
//	public PacketMap getPacketMap() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
