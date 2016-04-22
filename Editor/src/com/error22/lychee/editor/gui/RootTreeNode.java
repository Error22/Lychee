package com.error22.lychee.editor.gui;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.error22.lychee.editor.IProject;
import com.error22.lychee.editor.LycheeEditor;
import com.error22.lychee.editor.gui.Comparators.ProjectNameComparator;

public class RootTreeNode extends ExplorerTreeNode {
	private LycheeEditor editor;
	private UUID[] projectIds;

	public RootTreeNode(ExplorerTreeModel model, LycheeEditor editor) {
		super(model);
		this.editor = editor;
		projectIds = new UUID[0];
		updateModel();
	}

	public void updateModel() {
		IProject[] projects = editor.getProjects().toArray(new IProject[0]);
		Arrays.parallelSort(projects, new ProjectNameComparator());
		List<UUID> ids = Arrays.stream(projects).map(IProject::getId).collect(Collectors.toList());
		
		
		
//		List<Integer> toRemove = IntStream.range(0, projectIds.length).filter(index -> !ids.contains(projectIds[index]))
//				.boxed().collect(Collectors.toList());
//		remove(toRemove);
		
		int[] data = IntStream.range(0, projectIds.length).filter(index -> !ids.contains(projectIds[index])).toArray();
		int offset = 0;
		for(int index : data){
			remove(index + offset);
			offset++;
		}
		
		projectIds = Arrays.stream(projectIds).filter(id -> ids.contains(id)).toArray(size -> new UUID[size]);

		int originalPos = 0;
		for (int i = 0; i < projects.length; i++) {
			if (originalPos < projectIds.length && projectIds[originalPos].equals(projects[i].getId())) {
				originalPos++;
				continue;
			}
			insert(i, projects[i].getNode(this));
//			indices.add(originalPos);
//			nodes.add(new SourceTreeNode(this, entries[i]));
		}
		
//		List<Integer> indices = new ArrayList<Integer>();
//		List<ExplorerTreeNode> nodes = new ArrayList<ExplorerTreeNode>();
//
//		int originalPos = 0;
//		for (int i = 0; i < projects.length; i++) {
//			if (originalPos < projectIds.length && projectIds[originalPos] == projects[i].getId()) {
//				originalPos++;
//				continue;
//			}
//			indices.add(originalPos);
//			nodes.add(projects[i].getNode(this));
//		}
//		
//		insert(indices, nodes);
		projectIds = ids.stream().toArray(size -> new UUID[size]);
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

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public void onClick(int count) {
	}

}
