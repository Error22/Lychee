package com.error22.lychee.editor.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.swing.Icon;

import com.error22.lychee.editor.ISourceEntry;
import com.error22.lychee.editor.LycheeEditor;

public class JavaSourceFolder implements ISourceEntry {
	private UUID id;
	private String name;
	private HashMap<UUID, JavaPackage> packages;

	public JavaSourceFolder(UUID id, String name) {
		this.id = id;
		this.name = name;
		packages = new HashMap<UUID, JavaPackage>();
		
		JavaPackage p1 = new JavaPackage(UUID.randomUUID(), "com.company.example");
		packages.put(p1.getId(), p1);
		JavaPackage p2 = new JavaPackage(UUID.randomUUID(), "com.company.example.cool");
		packages.put(p2.getId(), p2);
		
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
		return new ArrayList<ISourceEntry>(packages.values());
	}

	@Override
	public boolean isFolder() {
		return true;
	}

	@Override
	public void doubleClicked() {
	}

	@Override
	public Icon getIcon() {
		return LycheeEditor.INSTANCE.getIcon("/resources/java/package_folder.gif");
	}

}
