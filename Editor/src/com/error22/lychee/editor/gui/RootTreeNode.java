package com.error22.lychee.editor.gui;

import java.util.Arrays;
import java.util.UUID;

import com.error22.lychee.editor.IProject;
import com.error22.lychee.editor.LycheeEditor;

public class RootTreeNode implements IExplorerTreeNode {
	private LycheeEditor editor;
	private UUID[] projectIds;

	public RootTreeNode(LycheeEditor editor) {
		this.editor = editor;
		updateModel();
	}

	public void updateModel() {
		Object[] projects = editor.getProjects().toArray();
		projectIds = new UUID[projects.length];
		for (int i = 0; i < projects.length; i++) {
			projectIds[i] = ((IProject) projects[i]).getId();
		}
		Arrays.parallelSort(projectIds, new ProjectListComparator(editor));
	}

	@Override
	public int getChildCount() {
		return projectIds.length;
	}

	@Override
	public Object getChild(int index) {
		return editor.getProject(projectIds[index]).getNode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof RootTreeNode;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public String toString() {
		return "Projects";
	}

}
