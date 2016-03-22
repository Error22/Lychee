package com.error22.lychee.server.managed;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.UUID;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.commons.RemappingClassAdapter;

public class ManagedJar {
	private static Logger log = LogManager.getLogger();
	private Project project;
	private UUID id;
	private File sourceFile, outputFile;
	private String name;
	private JarFile jar;
	private JarType type;
	private JarStatus status;
	private HashMap<UUID, ManagedClass> classes;

	public ManagedJar(Project project, UUID id, String path, JarType type) {
		this.project = project;
		this.id = id;
		sourceFile = new File(new File(project.getFolder(), "source"), path);
		outputFile = new File(new File(project.getFolder(), "output"), path);
		this.name = path;
		this.type = type;
		status = JarStatus.UnLoaded;
		classes = new HashMap<UUID, ManagedClass>();
	}

	public boolean load() {
		if (!sourceFile.exists()) {
			status = JarStatus.NotFound;
			log.warn("Jar "+sourceFile+" was not found!");
			return false;
		}

		try {
			jar = new JarFile(sourceFile, false);
			for (Enumeration<JarEntry> entries = jar.entries(); entries.hasMoreElements();) {
				JarEntry entry = entries.nextElement();
				String resource = entry.getName();

				if (entry.isDirectory()) {
					continue;
				} else if (resource.endsWith(".class")) {
					String path = resource.substring(0, resource.length() - ".class".length());
					String name = path.substring(path.lastIndexOf('/') + 1);

					UUID id = UUID.randomUUID();
					classes.put(id, new ManagedClass(project, this, id, resource, path, name));
				} else {
					log.warn("Unknown! " + resource);
				}
			}
			jar.close();
			

			status = JarStatus.Loaded;
			return true;
		} catch (Exception e) {
			status = JarStatus.Other;
			log.warn("Jar "+sourceFile+" had an unknown error!", e);
			return false;
		}
	}
	
	public void findDependencies(){
		
	}
	
	public InputStream getStream(String path) throws IOException{
		return jar.getInputStream(jar.getEntry(path));
	}

	public Project getProject() {
		return project;
	}

	public UUID getId() {
		return id;
	}

	public File getSourceFile() {
		return sourceFile;
	}

	public File getOutputFile() {
		return outputFile;
	}

	public String getName() {
		return name;
	}

	public JarType getType() {
		return type;
	}

	public JarStatus getStatus() {
		return status;
	}

	public HashMap<UUID, ManagedClass> getClasses() {
		return classes;
	}

}
