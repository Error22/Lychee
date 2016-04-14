package com.error22.lychee.editor;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.error22.lychee.editor.gui.EditorWindow;
import com.error22.lychee.editor.network.ClientNetworkHandler;
import com.error22.lychee.editor.network.ConnectionStatus;
import com.error22.lychee.network.NetworkClient;
import com.error22.lychee.util.Util;

public class LycheeEditor {
	private static Logger log = LogManager.getLogger();
	public static LycheeEditor INSTANCE;
	private EditorWindow editorWindow;
	private HashMap<UUID, IProject> projects;
	private ClientNetworkHandler networkHandler;
	private NetworkClient networkClient;
	private ConnectionStatus connectionStatus;

	public void init() {
		projects = new HashMap<UUID, IProject>();
		connectionStatus = ConnectionStatus.Disconnected;
		
		log.info("Loading GUI...");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		editorWindow = new EditorWindow(this);
		editorWindow.getFrame().setVisible(true);
	}

	public void connectToServer(String address, int port) {
		try {
			connectionStatus = ConnectionStatus.Connecting;
			networkHandler = new ClientNetworkHandler(this, address, port);
			networkClient = new NetworkClient();
			networkClient.connect(address, port, networkHandler);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void addProject(IProject project) {
		projects.put(project.getId(), project);
	}

	public Collection<IProject> getProjects() {
		return projects.values();
	}

	public IProject getProject(UUID id) {
		return projects.get(id);
	}

	public EditorWindow getEditorWindow() {
		return editorWindow;
	}

	public ClientNetworkHandler getNetworkHandler() {
		return networkHandler;
	}

	public NetworkClient getNetworkClient() {
		return networkClient;
	}

	public ConnectionStatus getConnectionStatus() {
		return connectionStatus;
	}

	public void setConnectionStatus(ConnectionStatus connectionStatus) {
		this.connectionStatus = connectionStatus;
	}

	public static void main(String[] args) {
		Util.tieSystemOutAndErrToLog();
		log.info("Starting!");
		INSTANCE = new LycheeEditor();
		INSTANCE.init();

	}

}
