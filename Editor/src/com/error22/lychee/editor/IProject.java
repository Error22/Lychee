package com.error22.lychee.editor;

import java.util.UUID;

import com.error22.lychee.editor.gui.ExplorerTreeNode;

public interface IProject {

	public UUID getId();

	public String getName();

	public ProjectType getType();

	public ExplorerTreeNode getNode(ExplorerTreeNode parent);

}
