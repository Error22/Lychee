package com.error22.lychee.editor.gui;

import javax.swing.Icon;

import com.error22.lychee.editor.IProject;

public class ProjectTreeNode extends ExplorerTreeNode {
	private IProject project;

	public ProjectTreeNode(ExplorerTreeNode parent, IProject project) {
		super(parent);
		this.project = project;
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

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public void onClick(int count) {
	}

	@Override
	public Icon getIcon() {
		return project.getIcon();
	}
	
}
