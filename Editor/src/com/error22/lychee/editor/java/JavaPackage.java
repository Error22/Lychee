package com.error22.lychee.editor.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.error22.lychee.editor.ISourceEntry;

public class JavaPackage implements ISourceEntry {
	private UUID id;
	private String name;
	private HashMap<UUID, JavaFile> files;

	public JavaPackage(UUID id, String name) {
		this.id = id;
		this.name = name;
		files = new HashMap<UUID, JavaFile>();
		
		JavaFile f1 = new JavaFile(UUID.randomUUID(), this, "Example");
		files.put(f1.getId(), f1);
		JavaFile f2 = new JavaFile(UUID.randomUUID(), this, "OtherExample");
		files.put(f2.getId(), f2);
		
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
		return new ArrayList<ISourceEntry>(files.values());
	}

	@Override
	public boolean isFolder() {
		return true;
	}

	@Override
	public void doubleClicked() {
	}

}
