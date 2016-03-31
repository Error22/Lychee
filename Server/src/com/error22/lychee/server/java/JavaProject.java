package com.error22.lychee.server.java;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.error22.lychee.server.IProject;
import com.error22.lychee.server.ProjectType;
import com.error22.lychee.server.managed.JarType;
import com.error22.lychee.server.managed.ManagedClass;
import com.error22.lychee.server.managed.ManagedJar;

public class JavaProject implements IProject {
	private static Logger log = LogManager.getLogger();
	private UUID id;
	private String name;
	private File folder;
	private HashMap<UUID, ManagedJar> jars;
	private HashMap<UUID, ManagedClass> classes;

	public JavaProject(UUID id, String name, File folder) {
		this.id = id;
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

	public File getFolder() {
		return folder;
	}

	public Collection<ManagedJar> getJars() {
		return jars.values();
	}

	public Collection<ManagedClass> getClasses() {
		return classes.values();
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
	public ProjectType getType() {
		return ProjectType.Java;
	}

}
