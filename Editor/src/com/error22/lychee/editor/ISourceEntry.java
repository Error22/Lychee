package com.error22.lychee.editor;

import java.util.List;
import java.util.UUID;

public interface ISourceEntry {

	public UUID getId();

	public String getName();
	
	public List<ISourceEntry> getEntries();
	
	public boolean isFolder();

	public void doubleClicked();
	
}
