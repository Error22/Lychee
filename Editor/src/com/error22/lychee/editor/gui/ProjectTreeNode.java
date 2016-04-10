package com.error22.lychee.editor.gui;

import com.error22.lychee.editor.IProject;

public class ProjectTreeNode implements IExplorerTreeNode {
	private IProject project;
	private IExplorerTreeNode[] children;

	public ProjectTreeNode(IProject project, IExplorerTreeNode[] children) {
		this.project = project;
		this.children = children;
	}

	@Override
	public int getChildCount() {
		return children.length;
	}

	@Override
	public Object getChild(int index) {
		return children[index];
	}

	@Override
	public int hashCode() {
		return project.getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectTreeNode other = (ProjectTreeNode) obj;
		return project.getId().equals(other.project.getId());
	}

	@Override
	public String toString() {
		return project.getName();
	}

}
