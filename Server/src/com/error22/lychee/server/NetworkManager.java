package com.error22.lychee.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkManager implements Runnable{
	private int port;
	private Server server;
	private ServerSocket serverSocket;
	private ArrayList<Thread> threads;
	
	public NetworkManager(Server server, int port) {
		this.port = port;
		this.server = server;
	}

	public boolean start(){
		try {
			serverSocket = new ServerSocket(port);
			new Thread(this).start();
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				Socket socket = serverSocket.accept();
				Thread t = new Thread(new NetworkClient(socket));
				threads.add(t);
				t.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public class NetworkClient implements Runnable{
		private Socket socket;
		
		public NetworkClient(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			
			
		}
		
	}
	
	
	
	
	
	
}
