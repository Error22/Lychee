package com.error22.lychee.editor.java;

import java.util.List;
import java.util.UUID;

import com.error22.lychee.editor.ISourceEntry;

public class JavaFile implements ISourceEntry {
	private UUID id;
	private String name;

	public JavaFile(UUID id, String name) {
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
	public List<ISourceEntry> getEntries() {
		return null;
	}

	@Override
	public boolean isFolder() {
		return false;
	}

	@Override
	public void doubleClicked() {
		System.out.println("Open java file please!");
	}
	
	

}
