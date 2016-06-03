package com.error22.lychee.editor.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.error22.lychee.editor.ISourceEntry;
import com.error22.lychee.editor.gui.Comparators.SourceEntryNameComparator;

public class SourceTreeNode extends ExplorerTreeNode {
	private ISourceEntry entry;
	private List<UUID> childrenIds;

	public SourceTreeNode(ExplorerTreeNode parent, ISourceEntry entry) {
		super(parent);
		this.entry = entry;
		childrenIds = new ArrayList<UUID>();
		updateModel();
	}

	public void updateModel() {
		if (!entry.isFolder()) {
			return;
		}

		List<ISourceEntry> entries = entry.getEntries();
		Collections.sort(entries, new SourceEntryNameComparator());
		List<UUID> ids = entries.stream().map(ISourceEntry::getId).collect(Collectors.toList());

		int[] data = IntStream.range(0, childrenIds.size()).filter(index -> !ids.contains(childrenIds.get(index)))
				.toArray();
		int offset = 0;
		for (int index : data) {
			remove(index + offset);
			offset++;
		}

		childrenIds = childrenIds.stream().filter(id -> ids.contains(id)).collect(Collectors.toList());

		int originalPos = 0;
		for (int i = 0; i < entries.size(); i++) {
			if (originalPos < childrenIds.size() && childrenIds.get(originalPos).equals(entries.get(i).getId())) {
				originalPos++;
				continue;
			}

			if (childrenIds.contains(entries.get(i).getId())) {
				throw new RuntimeException("Some sort of reorder has occured?");
			}
			insert(i, new SourceTreeNode(this, entries.get(i)));
		}

		childrenIds = ids;
	}

	@Override
	public int hashCode() {
		return entry.getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SourceTreeNode other = (SourceTreeNode) obj;
		return entry.getId().equals(other.entry.getId());
	}

	@Override
	public String toString() {
		return entry.getName();
	}

	@Override
	public boolean isLeaf() {
		return !entry.isFolder();
	}

	@Override
	public void onClick(int count) {
		if(count == 2){
			entry.doubleClicked();
		}
	}
}
