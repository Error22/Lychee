package com.error22.lychee.editor.java;

import java.util.List;
import java.util.UUID;

import javax.swing.Icon;

import com.error22.lychee.editor.ISourceEntry;
import com.error22.lychee.editor.LycheeEditor;
import com.error22.lychee.editor.gui.EditorWindow;

public class JavaFile implements ISourceEntry {
	private UUID id;
	private JavaPackage parent;
	private String name;
	private JavaEditor editor;

	public JavaFile(UUID id, JavaPackage parent, String name) {
		this.id = id;
		this.name = name;
		this.parent = parent;
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
	public List<ISourceEntry> getEntries() {
		return null;
	}

	@Override
	public boolean isFolder() {
		return false;
	}

	@Override
	public void doubleClicked() {
		EditorWindow window = LycheeEditor.INSTANCE.getEditorWindow();
		if (editor == null) {
			editor = new JavaEditor(this);
		}
		window.openEditor(editor);
	}

	public void destroyEditor() {
		editor = null;
	}

	public String getPath() {
		return parent.getName() + "." + getName();
	}

	@Override
	public Icon getIcon() {
		return LycheeEditor.INSTANCE.getIcon("/resources/java/source.png");
	}

}
