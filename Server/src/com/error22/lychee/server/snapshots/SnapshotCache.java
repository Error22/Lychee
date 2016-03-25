package com.error22.lychee.server.snapshots;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.error22.lychee.server.managed.Project;

public class SnapshotCache {
	private static Logger log = LogManager.getLogger();
	private Project project;
	private HashMap<String, String> classMap;

	public SnapshotCache(Project project) {
		this.project = project;
		classMap = new HashMap<String, String>();
	}

	public SnapshotCache(SnapshotCache cache) {
		this(cache.project);
		classMap.putAll(cache.getClassMap());
	}

	public HashMap<String, String> getClassMap() {
		return classMap;
	}

	public void apply(IAction action) {
		log.info("Applying " + action);
		if (action instanceof ActionRenameClass) {
			ActionRenameClass act = (ActionRenameClass) action;
			classMap.put(act.getSource(), act.getTarget());
			
			
		} else {
			throw new RuntimeException("Unknown action! " + action);
		}
	}

}
