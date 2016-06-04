package com.error22.lychee.editor.java;

import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.error22.lychee.editor.IEditor;
import com.error22.lychee.editor.gui.EditorWindow;

public class JavaEditor implements IEditor {
	private JavaFile file;
	private JSplitPane mainSplitPane;
	private RSyntaxTextArea textArea;

	public JavaEditor(JavaFile file) {
		this.file = file;
		mainSplitPane = new JSplitPane();
		mainSplitPane.setContinuousLayout(true);
		mainSplitPane.setResizeWeight(0.5);

		textArea = new RSyntaxTextArea();
		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		textArea.setEditable(false);
		textArea.setAnimateBracketMatching(true);
		textArea.setAntiAliasingEnabled(true);
		textArea.setClearWhitespaceLinesEnabled(true);
		textArea.setCodeFoldingEnabled(true);
		textArea.setPaintMarkOccurrencesBorder(true);
		textArea.setPaintMatchedBracketPair(true);
		textArea.setMarkOccurrences(true);
		textArea.getCaret().addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent paramChangeEvent) {
				textArea.getCaret().setVisible(true);
			}
		});

		textArea.setText(
				"package abc;\r\n\r\npublic class Example{\r\n\tprivate int example = 0;\r\n\t\r\n\tpublic void example(){\r\n\t\tSystem.out.println(\"hi!\");\r\n\t}\r\n\t\r\n\tpublic static void main(String[] args){\r\n\t\texample();\r\n\t}\r\n}");

		textArea.convertSpacesToTabs();

		theme.apply(textArea);

		RTextScrollPane scrollPane = new RTextScrollPane(textArea, true);
		scrollPane.getGutter().setBookmarkingEnabled(true);
		scrollPane.getGutter()
				.setBookmarkIcon(new ImageIcon(EditorWindow.class.getResource("/resources/menu/connection.gif")));
		scrollPane.setIconRowHeaderEnabled(true);
		scrollPane.setFoldIndicatorEnabled(true);
		mainSplitPane.setLeftComponent(scrollPane);

		JScrollPane treeScrollPane = new JScrollPane();
		mainSplitPane.setRightComponent(treeScrollPane);

		JTree tree = new JTree();
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Example") {
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
		}));
		treeScrollPane.setViewportView(tree);
	}

	@Override
	public String getTitle() {
		return file.getName();
	}

	@Override
	public String getCloseToolTip() {
		return "Close " + file.getName();
	}

	@Override
	public String getHoverToolTip() {
		return file.getPath();
	}

	@Override
	public ImageIcon getIcon() {
		return new ImageIcon(JavaEditor.class.getResource("/resources/file_types/java.png"));
	}

	@Override
	public JComponent getMainPane() {
		return mainSplitPane;
	}

	@Override
	public void destroy() {
		file.destroyEditor();
	}

	@Override
	public int hashCode() {
		return file.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return file.equals(((JavaEditor) obj).file);
	}

	private static Theme theme;

	static {
		try {
			theme = Theme.load(JavaEditor.class.getResourceAsStream("/org/fife/ui/rsyntaxtextarea/themes/eclipse.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
