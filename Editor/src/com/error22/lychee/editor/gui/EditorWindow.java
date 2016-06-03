package com.error22.lychee.editor.gui;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.error22.lychee.editor.LycheeEditor;
import com.error22.lychee.editor.java.JavaEditor;

public class EditorWindow {
	private LycheeEditor editor;
	private JFrame frame;
	private JTextField searchBox;
	private JTable table;
	private JTextField messageBox;
	private JTable chatTable;
	private ConnectDialog connectDialog;
	private ExplorerTreeModel explorerTreeModel;

	/**
	 * Create the application.
	 * 
	 * @param lycheeEditor
	 */
	public EditorWindow(LycheeEditor editor) {
		this.editor = editor;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Lychee 1.0 - Error22");
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JSplitPane mainSplitPane = new JSplitPane();
		mainSplitPane.setResizeWeight(0.2);
		mainSplitPane.setContinuousLayout(true);
		frame.getContentPane().add(mainSplitPane, BorderLayout.CENTER);

		JScrollPane leftScrollPane = new JScrollPane();
		mainSplitPane.setLeftComponent(leftScrollPane);

		searchBox = new JTextField();
		leftScrollPane.setColumnHeaderView(searchBox);
		searchBox.setText("Search...");
		searchBox.setColumns(10);

		JTree projectTree = new JTree();
		projectTree.setModel(explorerTreeModel = new ExplorerTreeModel(editor));
		projectTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int selRow = projectTree.getRowForLocation(e.getX(), e.getY());
				TreePath selPath = projectTree.getPathForLocation(e.getX(), e.getY());
				if (selRow != -1) {
					((ExplorerTreeNode) selPath.getLastPathComponent()).onClick(e.getClickCount());
				}
			}

		});
		// tree.setCellRenderer(new CellRenderer());
		leftScrollPane.setViewportView(projectTree);

		JSplitPane centerSplitPane = new JSplitPane();
		centerSplitPane.setContinuousLayout(true);
		centerSplitPane.setResizeWeight(0.8);
		centerSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		mainSplitPane.setRightComponent(centerSplitPane);

		JTabbedPane editorTabbedPane = new ClosableTabbedPane(JTabbedPane.TOP);
		centerSplitPane.setLeftComponent(editorTabbedPane);

		// editorTabbedPane.addTab("New tab",
		// new
		// ImageIcon(EditorWindow.class.getResource("/resources/file_types/java.png")),
		// splitPane_2, null);

		editorTabbedPane.addTab("Welcome", new JScrollPane(new JTextArea(
				"Welcome to Lychee\nYou will need to connect to a server.\nYou can close this tab using the 'X' button.")));

		JTabbedPane bottomTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		centerSplitPane.setRightComponent(bottomTabbedPane);

		JScrollPane eventsScrollPane = new JScrollPane();
		bottomTabbedPane.addTab("Events", null, eventsScrollPane, null);

		table = new JTable();
		table.setModel(
				new DefaultTableModel(
						new Object[][] { { "07:21 17/03/2016", "Connected to server", "Client" },
								{ "07:22 17/03/2016", "Catched up with server", "Client" },
								{ "07:23 17/03/2016",
										"CoolGuy remapped 'String rename(String abc, Something example)' to 'renameOld'",
										"my.company.project.a" }, },
				new String[] { "Time & Date", "Message", "Source" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(0).setMaxWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(428);
		eventsScrollPane.setViewportView(table);

		JPanel chatPanel = new JPanel();
		bottomTabbedPane.addTab("Chat", null, chatPanel, null);
		chatPanel.setLayout(new BorderLayout(0, 0));

		JPanel chatBottomPanel = new JPanel();
		chatPanel.add(chatBottomPanel, BorderLayout.SOUTH);
		chatBottomPanel.setLayout(new BorderLayout(0, 0));

		messageBox = new JTextField();
		chatBottomPanel.add(messageBox, BorderLayout.CENTER);
		messageBox.setColumns(10);

		JButton sendButton = new JButton("Send");
		chatBottomPanel.add(sendButton, BorderLayout.EAST);

		JScrollPane chatScrollPane = new JScrollPane();
		chatPanel.add(chatScrollPane, BorderLayout.CENTER);

		chatTable = new JTable();
		chatTable.setModel(new DefaultTableModel(new Object[][] { { "07:37 17/03/2016", "Hello" }, },
				new String[] { "Time & Date", "Message" }));
		chatTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		chatTable.getColumnModel().getColumn(0).setMaxWidth(100);
		chatTable.getColumnModel().getColumn(1).setPreferredWidth(281);
		chatScrollPane.setViewportView(chatTable);

		JLabel status = new JLabel("Starting...");
		frame.getContentPane().add(status, BorderLayout.SOUTH);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnConnection = new JMenu("Connection");
		mnConnection.setIcon(new ImageIcon(EditorWindow.class.getResource("/resources/menu/connection.gif")));
		menuBar.add(mnConnection);

		connectDialog = new ConnectDialog();
		JMenuItem mntmConnect = new JMenuItem("Connect");
		mntmConnect.setIcon(new ImageIcon(EditorWindow.class.getResource("/resources/menu/connect.gif")));
		mntmConnect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!connectDialog.isVisible()) {
					connectDialog.setVisible(true);
				}
			}
		});
		mnConnection.add(mntmConnect);

		JMenuItem mntmDisconnect = new JMenuItem("Disconnect");
		mntmDisconnect.setIcon(new ImageIcon(EditorWindow.class.getResource("/resources/menu/disconnect.gif")));
		mnConnection.add(mntmDisconnect);
	}

	public ExplorerTreeModel getExplorerTreeModel() {
		return explorerTreeModel;
	}

	public JFrame getFrame() {
		return frame;
	}

}
