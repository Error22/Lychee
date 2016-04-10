package com.error22.lychee.editor.gui;

import java.util.Comparator;
import java.util.UUID;

import com.error22.lychee.editor.LycheeEditor;

public class ProjectListComparator implements Comparator<UUID> {
	private LycheeEditor editor;

	public ProjectListComparator(LycheeEditor editor) {
		this.editor = editor;
	}

	@Override
	public int compare(UUID o1, UUID o2) {
		return editor.getProject(o1).getName().compareTo(editor.getProject(o2).getName());
	}

}
