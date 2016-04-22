package com.error22.lychee.editor.gui;

import java.util.Comparator;

import com.error22.lychee.editor.IProject;
import com.error22.lychee.editor.ISourceEntry;

public class Comparators {
	
	public static class ProjectNameComparator implements Comparator<IProject> {
		@Override
		public int compare(IProject o1, IProject o2) {
			return o1.getName().compareTo(o2.getName());
		}
	}
	
	public static class SourceEntryNameComparator implements Comparator<ISourceEntry> {
		@Override
		public int compare(ISourceEntry o1, ISourceEntry o2) {
			return o1.getName().compareTo(o2.getName());
		}
	}
	
}
