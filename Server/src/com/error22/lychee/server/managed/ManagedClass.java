package com.error22.lychee.server.managed;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;

import com.error22.lychee.server.java.JavaProject;

public class ManagedClass {
	private static Logger log = LogManager.getLogger();
	private JavaProject project;
	private ManagedJar jar;
	private UUID id;
	private String resource, path, name;
	private HashMap<UUID, ManagedClass> dependencies, dependents;

	public ManagedClass(JavaProject project, ManagedJar jar, UUID id, String resource, String path, String name) {
		this.project = project;
		this.jar = jar;
		this.id = id;
		this.resource = resource;
		this.path = path;
		this.name = name;
		dependencies = new HashMap<UUID, ManagedClass>();
		dependents = new HashMap<UUID, ManagedClass>();

		try {
			ClassReader cr = new ClassReader(jar.getStream(resource));
			// ClassNode node = new ClassNode();
			// cr.accept(node, 0);
			log.info("");
			log.info("Processing " + resource);

			cr.accept(new ClassScanner(), 0);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void scanClass() {

	}

	public boolean findDependencies() {

		try {
			jar.getStream(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

		// for(ManagedClass mclass : project.getClasses().values()){
		// if(mclass.id.equals(id))
		// return;
		//
		//
		//
		//
		// }
	}

	public JavaProject getProject() {
		return project;
	}

	public ManagedJar getJar() {
		return jar;
	}

	public UUID getId() {
		return id;
	}

	public String getResource() {
		return resource;
	}

	public String getPath() {
		return path;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ManagedClass other = (ManagedClass) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
