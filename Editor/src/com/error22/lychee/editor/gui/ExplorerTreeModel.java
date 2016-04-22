package com.error22.lychee.editor.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.error22.lychee.editor.LycheeEditor;

public class ExplorerTreeModel implements TreeModel {
	private static Logger log = LogManager.getLogger();
	private LycheeEditor editor;
	private RootTreeNode root;
	private List<TreeModelListener> modelListeners;

	public ExplorerTreeModel(LycheeEditor editor) {
		this.editor = editor;
		modelListeners = new ArrayList<TreeModelListener>();
		root = new RootTreeNode(this, editor);
	}

	@Override
	public Object getRoot() {
		return root;
	}

	public RootTreeNode getRootNode() {
		return root;
	}

	@Override
	public Object getChild(Object parent, int index) {
		if (parent instanceof ExplorerTreeNode) {
			Object obj = ((ExplorerTreeNode) parent).getChild(index);

			if (obj == null) {
				log.info(" parent " + parent + " gave a null child!");
			}
			return obj;
		} else {
			throw new RuntimeException("Unknown parent type " + parent.getClass());
		}
	}

	@Override
	public int getChildCount(Object parent) {
		if (parent instanceof ExplorerTreeNode) {
			return ((ExplorerTreeNode) parent).getChildCount();
		} else {
			throw new RuntimeException("Unknown parent type " + parent.getClass());
		}
	}

	@Override
	public boolean isLeaf(Object node) {
		return ((ExplorerTreeNode)node).isLeaf();
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		log.info("valueForPathChanged");
		throw new RuntimeException("valueForPathChanged");
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		log.info("getIndexOfChild");
		return ((ExplorerTreeNode)parent).getChildIndex((ExplorerTreeNode) child);
	}
	
	public List<TreeModelListener> getModelListeners() {
		return modelListeners;
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		modelListeners.add(l);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		modelListeners.remove(l);
	}

}
