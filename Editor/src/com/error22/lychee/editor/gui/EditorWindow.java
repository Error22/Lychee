package com.error22.lychee.editor.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.error22.lychee.editor.LycheeEditor;

public class EditorWindow {
	private LycheeEditor editor;
	private JFrame frame;
	private JTextField searchBox;
	private JTable table;
	private JTextField messageBox;
	private JTable chatTable;
	private ConnectDialog connectDialog;

	/**
	 * Create the application.
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
//		projectTree.setModel(new DefaultTreeModel(
//			new DefaultMutableTreeNode("Projects") {
//				{
//					DefaultMutableTreeNode node_1;
//					DefaultMutableTreeNode node_2;
//					node_1 = new DefaultMutableTreeNode("StarMade");
//						node_2 = new DefaultMutableTreeNode("com.example");
//							node_2.add(new DefaultMutableTreeNode("StarMade.class"));
//						node_1.add(node_2);
//						node_2 = new DefaultMutableTreeNode("com.example.Lol");
//							node_2.add(new DefaultMutableTreeNode("Content"));
//						node_1.add(node_2);
//					add(node_1);
//					add(new DefaultMutableTreeNode("Lib1"));
//				}
//			}
//		));
		projectTree.setModel(new ExplorerTreeModel(editor));
		
//		tree.setCellRenderer(new CellRenderer());
		leftScrollPane.setViewportView(projectTree);
		
		JSplitPane centerSplitPane = new JSplitPane();
		centerSplitPane.setContinuousLayout(true);
		centerSplitPane.setResizeWeight(0.8);
		centerSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		mainSplitPane.setRightComponent(centerSplitPane);
		
		JTabbedPane editorTabbedPane = new ClosableTabbedPane(JTabbedPane.TOP);
		centerSplitPane.setLeftComponent(editorTabbedPane);
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setContinuousLayout(true);
		splitPane_2.setResizeWeight(0.5);
		editorTabbedPane.addTab("New tab", new ImageIcon(EditorWindow.class.getResource("/resources/file_types/java.png")), splitPane_2, null);
		
		for(int i = 0; i < 20; i++)
		editorTabbedPane.addTab("New tab - "+i, new ImageIcon(EditorWindow.class.getResource("/resources/file_types/java.png")), new JButton("abc"), null);
		
		
//		JScrollPane scrollPane = new JScrollPane();
		
		Theme theme = null;
		try {
			theme = Theme.load(EditorWindow.class.getResourceAsStream("/org/fife/ui/rsyntaxtextarea/themes/eclipse.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RSyntaxTextArea area = new RSyntaxTextArea();
		area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		area.setEditable(false);
		area.setAnimateBracketMatching(true);
		area.setAntiAliasingEnabled(true);
		area.setClearWhitespaceLinesEnabled(true);
		area.setCodeFoldingEnabled(true);
		area.setPaintMarkOccurrencesBorder(true);
		area.setPaintMatchedBracketPair(true);
		area.setMarkOccurrences(true);
		area.getCaret().addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent paramChangeEvent) {
				area.getCaret().setVisible(true);
				
			}
		});
		
		
		area.setText("package abc;\r\n\r\npublic class Example{\r\n\tprivate int example = 0;\r\n\t\r\n\tpublic void example(){\r\n\t\tSystem.out.println(\"hi!\");\r\n\t}\r\n\t\r\n\tpublic static void main(String[] args){\r\n\t\texample();\r\n\t}\r\n}");
		
		area.convertSpacesToTabs();
		
		
		theme.apply(area);
		
		RTextScrollPane spane = new RTextScrollPane(area, true);
		spane.getGutter().setBookmarkingEnabled(true);
		spane.getGutter().setBookmarkIcon(new ImageIcon(EditorWindow.class.getResource("/resources/menu/connection.gif")));
		spane.setIconRowHeaderEnabled(true);
		spane.setFoldIndicatorEnabled(true);
		splitPane_2.setLeftComponent(spane);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		splitPane_2.setRightComponent(scrollPane_2);
		
		JTree tree_1 = new JTree();
		tree_1.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Example") {
				{
					DefaultMutableTreeNode node_1;
					DefaultMutableTreeNode node_2;
					node_1 = new DefaultMutableTreeNode("variables");
						node_1.add(new DefaultMutableTreeNode("local public"));
						node_2 = new DefaultMutableTreeNode("local private");
							node_2.add(new DefaultMutableTreeNode("time"));
							node_2.add(new DefaultMutableTreeNode("name"));
						node_1.add(node_2);
						node_1.add(new DefaultMutableTreeNode("local protected"));
						node_1.add(new DefaultMutableTreeNode("static public"));
						node_1.add(new DefaultMutableTreeNode("static private"));
						node_1.add(new DefaultMutableTreeNode("static protected"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("methods");
						node_1.add(new DefaultMutableTreeNode("local public"));
						node_1.add(new DefaultMutableTreeNode("local private"));
						node_1.add(new DefaultMutableTreeNode("local protected"));
						node_2 = new DefaultMutableTreeNode("static public");
							node_2.add(new DefaultMutableTreeNode("String main(String[] args)"));
						node_1.add(node_2);
						node_1.add(new DefaultMutableTreeNode("static private"));
						node_1.add(new DefaultMutableTreeNode("static protected"));
					add(node_1);
					add(new DefaultMutableTreeNode("patches"));
				}
			}
		));
		scrollPane_2.setViewportView(tree_1);
		
		JTabbedPane bottomTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		centerSplitPane.setRightComponent(bottomTabbedPane);
		
		JScrollPane eventsScrollPane = new JScrollPane();
		bottomTabbedPane.addTab("Events", null, eventsScrollPane, null);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"07:21 17/03/2016", "Connected to server", "Client"},
				{"07:22 17/03/2016", "Catched up with server", "Client"},
				{"07:23 17/03/2016", "CoolGuy remapped 'String rename(String abc, Something example)' to 'renameOld'", "my.company.project.a"},
			},
			new String[] {
				"Time & Date", "Message", "Source"
			}
		));
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
		chatTable.setModel(new DefaultTableModel(
			new Object[][] {
				{"07:37 17/03/2016", "Hello"},
			},
			new String[] {
				"Time & Date", "Message"
			}
		));
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
				if(!connectDialog.isVisible()){
					connectDialog.setVisible(true);
				}
			}
		});
		mnConnection.add(mntmConnect);
		
		JMenuItem mntmDisconnect = new JMenuItem("Disconnect");
		mntmDisconnect.setIcon(new ImageIcon(EditorWindow.class.getResource("/resources/menu/disconnect.gif")));
		mnConnection.add(mntmDisconnect);
	}
	
	public JFrame getFrame() {
		return frame;
	}

}
