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
import com.error22.lychee.network.packets.ProjectList;
import com.error22.lychee.network.packets.RequestProjectList;
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

		IProject project = new JavaProject(this, UUID.randomUUID(), "123");
		projects.put(project.getId(), project);
		
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

	public void completeConnection() {
		new Thread("ServerConnection") {
			@Override
			public void run() {
				ProjectList list = (ProjectList) networkHandler
						.sendPairedPacket(new RequestProjectList(true, UUID.randomUUID()));

				log.info("got " + list);

				for (int i = 0; i < list.getIds().length; i++) {
					UUID id = list.getIds()[i];
					String name = list.getNames()[i];
					String type = list.getTypes()[i];

					log.info("Loading project " + id + ":" + name + " of type " + type);

					addProject(new JavaProject(LycheeEditor.this, id, name));
				}

			}
		}.start();
	}

	public void addProject(IProject project) {
		projects.put(project.getId(), project);
		editorWindow.getExplorerTreeModel().getRootNode().updateModel();
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
