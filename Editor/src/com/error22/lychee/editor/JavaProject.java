package com.error22.lychee.editor;

import java.util.UUID;

import com.error22.lychee.editor.gui.IExplorerTreeNode;
import com.error22.lychee.editor.gui.ProjectTreeNode;
import com.error22.lychee.editor.gui.SourceTreeNode;

public class JavaProject implements IProject{
	private UUID id;
	private String name;
	private ProjectTreeNode node;
	
	public JavaProject(UUID id, String name) {
		this.id = id;
		this.name = name;
		IExplorerTreeNode[] children = new IExplorerTreeNode[1];
		children[0] = new SourceTreeNode(new JavaSourceFolder(UUID.randomUUID(), "src"));
		node = new ProjectTreeNode(this, children);
	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public IExplorerTreeNode getNode() {
		return node;
	}

	@Override
	public ProjectType getType() {
		return ProjectType.Java;
	}

	

}
