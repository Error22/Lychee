package com.error22.lychee.server.snapshots;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.error22.lychee.server.java.JavaProject;

public class Snapshot {
	private UUID id;
	private JavaProject project;
	private Snapshot parent;
	private IAction action;
	private SnapshotCache cache;

	public Snapshot(JavaProject project, UUID id, Snapshot parent, IAction action) {
		this.id = id;
		this.project = project;
		this.parent = parent;
		this.action = action;
	}

	public void generateCache(boolean allowDerivateCache) {
		if (allowDerivateCache && parent != null && parent.cache != null) {
			cache = new SnapshotCache(parent.cache);
			cache.apply(action);
		} else {
			cache = new SnapshotCache(project);

			ArrayList<IAction> actions = new ArrayList<IAction>();
			addUptreeActions(actions, true);
			for (IAction _action : actions) {
				cache.apply(_action);
			}

			cache.apply(action);
		}
	}

	public SnapshotCache getCache() {
		return cache;
	}

	public Snapshot getParent() {
		return parent;
	}

	public UUID getId() {
		return id;
	}

	public IAction getAction() {
		return action;
	}

	public void addUptreeActions(List<IAction> actions, boolean start) {
		if (parent != null) {
			parent.addUptreeActions(actions, false);
		}
		if (!start) {
			actions.add(action);
		}
	}

}
