package com.error22.lychee.editor.gui;

import javax.swing.Icon;
import javax.swing.event.TreeModelEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ExplorerTreeNode {
	private static Logger log = LogManager.getLogger();
	private Object[] path;
	private ExplorerTreeModel model;
	private ExplorerTreeNode parent;
	private ExplorerTreeNode[] children;

	public ExplorerTreeNode(ExplorerTreeModel model) {
		this.model = model;
		this.path = new Object[] { this };
		children = new ExplorerTreeNode[0];
	}

	public ExplorerTreeNode(ExplorerTreeNode parent) {
		this.model = parent.getModel();
		this.parent = parent;

		Object[] parentPath = parent.getPath();
		this.path = new Object[parentPath.length + 1];
		System.arraycopy(parentPath, 0, path, 0, parentPath.length);
		path[parentPath.length] = this;

		children = new ExplorerTreeNode[0];
	}

	public void add(ExplorerTreeNode node) {
		ExplorerTreeNode[] newChildren = new ExplorerTreeNode[children.length + 1];
		System.arraycopy(children, 0, newChildren, 0, children.length);
		newChildren[children.length] = node;
		children = newChildren;

		TreeModelEvent event = new TreeModelEvent(this, getPath(), new int[children.length], new Object[] { node });
		getModel().getModelListeners().stream().forEach(Listener -> Listener.treeNodesInserted(event));
	}

	public void insert(int position, ExplorerTreeNode node) {
		ExplorerTreeNode[] newChildren = new ExplorerTreeNode[children.length + 1];

		if (position > 0) {
			System.arraycopy(children, 0, newChildren, 0, position);
		}
		newChildren[position] = node;
		if (position + 1 < children.length) {
			System.arraycopy(children, position, newChildren, position + 1, children.length - position);
		}

		children = newChildren;

		TreeModelEvent event = new TreeModelEvent(this, path, new int[] { position }, new ExplorerTreeNode[] { node });
		getModel().getModelListeners().stream().forEach(Listener -> Listener.treeNodesInserted(event));
	}

	public void remove(int position) {
		ExplorerTreeNode[] newChildren = new ExplorerTreeNode[children.length - 1];

		if (position > 0) {
			System.arraycopy(children, 0, newChildren, 0, position);
		}
		if (position + 1 < children.length) {
			System.arraycopy(children, position + 1, newChildren, position, children.length - position);
		}

		ExplorerTreeNode removing = children[position];

		children = newChildren;

		TreeModelEvent event = new TreeModelEvent(this, path, new int[] { position },
				new ExplorerTreeNode[] { removing });
		model.getModelListeners().forEach(listener -> listener.treeNodesRemoved(event));

	}

	public ExplorerTreeNode getParent() {
		return parent;
	}

	public int getChildCount() {
		return children.length;
	}

	public int getChildIndex(ExplorerTreeNode child) {
		for (int i = 0; i < children.length; i++) {
			if (children[i].equals(child)) {
				return i;
			}
		}
		return -1;
	}

	public ExplorerTreeNode getChild(int index) {
		return children[index];
	}

	public ExplorerTreeNode[] getChildren() {
		return children;
	}

	public Object[] getPath() {
		return path;
	}

	public ExplorerTreeModel getModel() {
		return model;
	}

	public abstract boolean isLeaf();

	public abstract void onClick(int count);

	public abstract Icon getIcon();

}
