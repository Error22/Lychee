package com.error22.lychee.editor;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class WelcomeEditor implements IEditor {
	private JScrollPane scrollPane;

	public WelcomeEditor() {
		scrollPane = new JScrollPane(new JTextArea(
				"Welcome to Lychee\nYou will need to connect to a server.\nYou can close this tab using the 'X' button."));
	}

	@Override
	public String getTitle() {
		return "Welcome";
	}

	@Override
	public String getCloseToolTip() {
		return "Close Welcome Screen";
	}

	@Override
	public String getHoverToolTip() {
		return "Welcome Screen";
	}

	@Override
	public ImageIcon getIcon() {
		return null;
	}

	@Override
	public JComponent getMainPane() {
		return scrollPane;
	}

	@Override
	public void destroy() {
		scrollPane = null;
	}

}
