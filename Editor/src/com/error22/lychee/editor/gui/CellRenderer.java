package com.error22.lychee.editor.gui;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class CellRenderer extends DefaultTreeCellRenderer {
	private static final long serialVersionUID = -5691181006363313993L;

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		ExplorerTreeNode node = (ExplorerTreeNode) value;
		setIcon(node.getIcon());

		return this;
	}

}