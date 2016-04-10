package com.error22.lychee.editor;

import java.util.UUID;

import com.error22.lychee.editor.gui.IExplorerTreeNode;

public interface IProject {

	public UUID getId();

	public String getName();

	public ProjectType getType();

	public IExplorerTreeNode getNode();

}
