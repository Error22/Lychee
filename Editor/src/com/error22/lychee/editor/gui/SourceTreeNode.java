package com.error22.lychee.editor.gui;

import com.error22.lychee.editor.ISourceFolder;

public class SourceTreeNode implements IExplorerTreeNode {
	private ISourceFolder sourceFolder;

	public SourceTreeNode(ISourceFolder folder) {
		this.sourceFolder = folder;
	}

	@Override
	public int getChildCount() {
		return 0;
	}

	@Override
	public Object getChild(int index) {
		return null;
	}
	
	@Override
	public int hashCode() {
		return sourceFolder.getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SourceTreeNode other = (SourceTreeNode) obj;
		return sourceFolder.getId().equals(other.sourceFolder.getId());
	}

	@Override
	public String toString() {
		return sourceFolder.getName();
	}

}
