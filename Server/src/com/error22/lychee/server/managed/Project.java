package com.error22.lychee.server.managed;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Project {
	private static Logger log = LogManager.getLogger();
	private String name;
	private File folder;
	private HashMap<UUID, ManagedJar> jars;
	private HashMap<UUID, ManagedClass> classes;

	public Project(String name, File folder) {
		this.name = name;
		this.folder = folder;
		jars = new HashMap<UUID, ManagedJar>();
		classes = new HashMap<UUID, ManagedClass>();
	}

	public void addJar(UUID id, String path, JarType type) {
		jars.put(id, new ManagedJar(this, id, path, type));

		// TODO: Handle project already loaded!
	}

	public boolean loadAllJars() {
		for (ManagedJar jar : jars.values()) {
			if (!jar.load()) {
				return false;
			}
			classes.putAll(jar.getClasses());
		}
		for (ManagedJar jar : jars.values()) {
			jar.findDependencies();
		}
		
		return true;
	}

	public List<ManagedClass> searchForClass(EnumSet<ClassSearchParam> params, String value, boolean caseSensitive) {
		ArrayList<ManagedClass> managedClasses = new ArrayList<ManagedClass>();
		if (params.contains(ClassSearchParam.UUID)) {
			UUID id = UUID.fromString(value);
			if (classes.containsKey(id)) {
				managedClasses.add(classes.get(id));
			}

		}
		if (!params.equals(EnumSet.of(ClassSearchParam.UUID))) {
			for (ManagedClass mclass : classes.values()) {

				if (params.contains(ClassSearchParam.Name)) {
					if (caseSensitive && mclass.getName().startsWith(value)) {
						managedClasses.add(mclass);
					} else if (!caseSensitive && mclass.getName().toLowerCase().startsWith(value.toLowerCase())) {
						managedClasses.add(mclass);
					}
				}
				if (params.contains(ClassSearchParam.Path)) {
					if (caseSensitive && mclass.getPath().startsWith(value)) {
						managedClasses.add(mclass);
					} else if (!caseSensitive && mclass.getPath().toLowerCase().startsWith(value.toLowerCase())) {
						managedClasses.add(mclass);
					}
				}
				if (params.contains(ClassSearchParam.Resource)) {
					if (caseSensitive && mclass.getResource().startsWith(value)) {
						managedClasses.add(mclass);
					} else if (!caseSensitive
							&& mclass.getResource().toLowerCase().startsWith(value.toLowerCase())) {
						managedClasses.add(mclass);
					}
				}
			}
		}

		return managedClasses;
	}

	public String getName() {
		return name;
	}

	public File getFolder() {
		return folder;
	}

	public HashMap<UUID, ManagedJar> getJars() {
		return jars;
	}

	public HashMap<UUID, ManagedClass> getClasses() {
		return classes;
	}

}
