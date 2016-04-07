package com.error22.lychee.editor;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.io.IOException;

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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

public class EditorWindow {

	private JFrame frame;
	private JTextField txtSearch;
	private JTable table;
	private JTextField textField;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditorWindow window = new EditorWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EditorWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 689, 456);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.2);
		splitPane.setContinuousLayout(true);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setLeftComponent(scrollPane_1);
		
		txtSearch = new JTextField();
		scrollPane_1.setColumnHeaderView(txtSearch);
		txtSearch.setText("Search...");
		txtSearch.setColumns(10);
		
		JTree tree = new JTree();
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Projects") {
				{
					DefaultMutableTreeNode node_1;
					DefaultMutableTreeNode node_2;
					node_1 = new DefaultMutableTreeNode("StarMade");
						node_2 = new DefaultMutableTreeNode("com.example");
							node_2.add(new DefaultMutableTreeNode("StarMade.class"));
						node_1.add(node_2);
						node_2 = new DefaultMutableTreeNode("com.example.Lol");
							node_2.add(new DefaultMutableTreeNode("Content"));
						node_1.add(node_2);
					add(node_1);
					add(new DefaultMutableTreeNode("Lib1"));
				}
			}
		));
//		tree.setCellRenderer(new CellRenderer());
		scrollPane_1.setViewportView(tree);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setContinuousLayout(true);
		splitPane_1.setResizeWeight(0.8);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setRightComponent(splitPane_1);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane_1.setLeftComponent(tabbedPane);
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setContinuousLayout(true);
		splitPane_2.setResizeWeight(0.8);
		tabbedPane.addTab("New tab", null, splitPane_2, null);
		
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
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		splitPane_1.setRightComponent(tabbedPane_1);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		tabbedPane_1.addTab("Events", null, scrollPane_3, null);
		
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
		scrollPane_3.setViewportView(table);
		
		JPanel panel = new JPanel();
		tabbedPane_1.addTab("Chat", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		textField = new JTextField();
		panel_1.add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		panel_1.add(btnNewButton, BorderLayout.EAST);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		panel.add(scrollPane_4, BorderLayout.CENTER);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{"07:37 17/03/2016", "Hello"},
			},
			new String[] {
				"Time & Date", "Message"
			}
		));
		table_1.getColumnModel().getColumn(0).setPreferredWidth(100);
		table_1.getColumnModel().getColumn(0).setMaxWidth(100);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(281);
		scrollPane_4.setViewportView(table_1);
		
		JLabel lblDisconnected = new JLabel("Disconnected");
		frame.getContentPane().add(lblDisconnected, BorderLayout.SOUTH);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnExample = new JMenu("File");
		menuBar.add(mnExample);
		
		JMenuItem menuItem_1 = new JMenuItem("123");
		mnExample.add(menuItem_1);
	}

}
