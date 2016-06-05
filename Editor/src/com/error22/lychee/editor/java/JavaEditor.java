package com.error22.lychee.editor.java;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextAreaEditorKit;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.fife.ui.rtextarea.RecordableTextAction;

import com.error22.lychee.editor.IEditor;
import com.error22.lychee.editor.LycheeEditor;
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

		int mod = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
		ResourceBundle msg = ResourceBundle.getBundle("org.fife.ui.rtextarea.RTextArea");

		RecordableTextAction copyAction = new RTextAreaEditorKit.CopyAction();
		copyAction.setProperties(msg, "Action.Copy");
		copyAction.setAccelerator(KeyStroke.getKeyStroke(67, mod));

		JPopupMenu menu = new JPopupMenu();
		menu.add(createPopupMenuItem(copyAction));
		menu.add(createPopupMenuItem(new RenameAction()));
		textArea.setPopupMenu(menu);

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
				node_1 = new DefaultMutableTreeNode("Variables");
				node_1.add(new DefaultMutableTreeNode("int example"));
				add(node_1);
				node_1 = new DefaultMutableTreeNode("Methods");
				node_1.add(new DefaultMutableTreeNode("public void example()"));
				node_1.add(new DefaultMutableTreeNode("public static void main(String[] args)"));
				add(node_1);
				add(new DefaultMutableTreeNode("Patches"));
			}
		}));
		treeScrollPane.setViewportView(tree);
	}

	// Test
	private class RenameAction extends TextAction {
		private static final long serialVersionUID = 5965781335594112567L;

		public RenameAction() {
			super("Rename");
		}

		private void showMessage(String message) {
			JOptionPane.showMessageDialog(LycheeEditor.INSTANCE.getEditorWindow().getFrame(), message, "Rename",
					JOptionPane.PLAIN_MESSAGE);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				JTextComponent tc = getTextComponent(e);

				int selStart = tc.getSelectionStart();
				int selEnd = tc.getSelectionEnd();
				if (selStart != selEnd) {
					char[] chars = tc.getText(selStart, selEnd - selStart).trim().toCharArray();
					for (char c : chars) {
						if (!Character.isLetterOrDigit(c)) {
							showMessage(
									"Invalid region selection!\nYou should only select the name of the object you want to rename.");
							return;
						}
					}
				}
				
				int caret = tc.getCaretPosition();
				Document doc = tc.getDocument();
				
				int end = caret;
				while (end < doc.getLength()) {
					char ch = doc.getText(end, 1).charAt(0);
					if (isFilenameChar(ch)) {
						end++;
					} else {
						break;
					}
				}
				
//				int start = caret;
//				Document doc = tc.getDocument();
//				while (start > 0) {
//					char ch = doc.getText(start - 1, 1).charAt(0);
//					if (isFilenameChar(ch)) {
//						start--;
//					} else {
//						break;
//					}
//				}
//				int end = caret;
//				while (end < doc.getLength()) {
//					char ch = doc.getText(end, 1).charAt(0);
//					if (isFilenameChar(ch)) {
//						end++;
//					} else {
//						break;
//					}
//				}
//				return doc.getText(start, end - start);
				

			} catch (BadLocationException ble) {
				ble.printStackTrace();
				showMessage("Internal Rename Error!\n" + ble.getMessage());
				return;
			}

			// String filename = null;
			//
			// // Get the name of the file to load. If there is a selection, use
			// // that as the file name, otherwise, scan for a filename around
			// // the caret.
			// try {
			// int selStart = tc.getSelectionStart();
			// int selEnd = tc.getSelectionEnd();
			// if (selStart != selEnd) {
			//
			// filename = tc.getText(selStart, selEnd - selStart);
			// } else {
			// filename = getFilenameAtCaret(tc);
			// }
			// } catch (BadLocationException ble) {
			// ble.printStackTrace();
			// UIManager.getLookAndFeel().provideErrorFeedback(tc);
			// return;
			// }
			//
			// System.out.println(new File(filename));

		}

		/**
		 * Gets the filename that the caret is sitting on. Note that this is a
		 * somewhat naive implementation and assumes filenames do not contain
		 * whitespace or other "funny" characters, but it will catch most common
		 * filenames.
		 * 
		 * @param tc
		 *            The text component to look at.
		 * @return The filename at the caret position.
		 * @throws BadLocationException
		 *             Shouldn't actually happen.
		 */
		public String getFilenameAtCaret(JTextComponent tc) throws BadLocationException {
			int caret = tc.getCaretPosition();
			int start = caret;
			Document doc = tc.getDocument();
			while (start > 0) {
				char ch = doc.getText(start - 1, 1).charAt(0);
				if (isFilenameChar(ch)) {
					start--;
				} else {
					break;
				}
			}
			int end = caret;
			while (end < doc.getLength()) {
				char ch = doc.getText(end, 1).charAt(0);
				if (isFilenameChar(ch)) {
					end++;
				} else {
					break;
				}
			}
			return doc.getText(start, end - start);
		}

		public boolean isFilenameChar(char ch) {
			return Character.isLetterOrDigit(ch) || ch == ':' || ch == '.' || ch == File.separatorChar;
		}

	}

	protected JMenuItem createPopupMenuItem(Action a) {
		JMenuItem item = new JMenuItem(a) {

			public void setToolTipText(String text) {
			}

		};
		item.setAccelerator(null);
		return item;
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
	public Icon getIcon() {
		return LycheeEditor.INSTANCE.getIcon("/resources/java/source.png");
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
