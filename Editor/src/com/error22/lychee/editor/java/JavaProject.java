package com.error22.lychee.editor.java;

import java.util.UUID;

import com.error22.lychee.editor.IProject;
import com.error22.lychee.editor.LycheeEditor;
import com.error22.lychee.editor.ProjectType;
import com.error22.lychee.editor.gui.ExplorerTreeNode;
import com.error22.lychee.editor.gui.ProjectTreeNode;
import com.error22.lychee.editor.gui.SourceTreeNode;

public class JavaProject implements IProject {
	private LycheeEditor editor;
	private UUID id;
	private String name;
	private ProjectTreeNode node;

	public JavaProject(LycheeEditor editor, UUID id, String name) {
		this.id = id;
		this.name = name;
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
	public ExplorerTreeNode getNode(ExplorerTreeNode parent) {
		if(node == null){
			node = new ProjectTreeNode(parent, this);
			node.add(new SourceTreeNode(node, new JavaSourceFolder(UUID.randomUUID(), "src")));
		}
		return node;
	}

	@Override
	public ProjectType getType() {
		return ProjectType.Java;
	}

}
