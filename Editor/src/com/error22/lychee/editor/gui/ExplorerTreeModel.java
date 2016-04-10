package com.error22.lychee.editor.gui;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.error22.lychee.editor.IProject;
import com.error22.lychee.editor.LycheeEditor;

public class ExplorerTreeModel implements TreeModel{
	private static Logger log = LogManager.getLogger();
	private LycheeEditor editor;
	private IExplorerTreeNode root;
//	private IProject[] rawProjects;
//	private ProjectTreeNode[] projectNodes;
	
	public ExplorerTreeModel(LycheeEditor editor) {
		this.editor = editor;
		root = new RootTreeNode(editor);
//		updateModel();
	}
	
//	public void updateModel(){
//		rawProjects = editor.getProjects().toArray(new IProject[0]);
//		projectNodes = new ProjectTreeNode[rawProjects.length];
//		
//		for(int i = 0; i < rawProjects.length; i++){
//			projectNodes[i] = new ProjectTreeNode(rawProjects[i]);
//		}
//	}

	@Override
	public Object getRoot() {
		log.info("getRoot");
		return root;
	}

	@Override
	public Object getChild(Object parent, int index) {
		log.info("getChild");
		if(parent instanceof IExplorerTreeNode){
			Object obj = ((IExplorerTreeNode)parent).getChild(index);
			
			if(obj == null){
				log.info(" parent "+parent +" gave a null child!");
			}
			return obj;
		}else{
			throw new RuntimeException("Unknown parent type "+parent.getClass());
		}
	}

	@Override
	public int getChildCount(Object parent) {
		log.info("getChildCount");
		if(parent instanceof IExplorerTreeNode){
			return ((IExplorerTreeNode)parent).getChildCount();
		}else{
			throw new RuntimeException("Unknown parent type "+parent.getClass());
		}
	}

	@Override
	public boolean isLeaf(Object node) {
		log.info("isLeaf");
		return false;
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		log.info("valueForPathChanged");
		
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		log.info("getIndexOfChild");
		return 0;
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		log.info("addTreeModelListener");
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		log.info("removeTreeModelListener");
	}

}
