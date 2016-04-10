package com.error22.lychee.editor;

import java.util.UUID;

public class JavaSourceFolder implements ISourceFolder{
	private UUID id;
	private String name;
	
	public JavaSourceFolder(UUID id, String name) {
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

}
