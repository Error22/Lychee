package com.error22.lychee.server.managed;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.commons.AnnotationRemapper;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.MethodRemapper;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.tree.ClassNode;

import com.strobel.assembler.metadata.ArrayTypeLoader;
import com.strobel.assembler.metadata.Buffer;
import com.strobel.assembler.metadata.ITypeLoader;
import com.strobel.decompiler.Decompiler;
import com.strobel.decompiler.DecompilerSettings;
import com.strobel.decompiler.PlainTextOutput;
import com.strobel.decompiler.languages.java.JavaFormattingOptions;

public class ManagedClass {
	private static Logger log = LogManager.getLogger();
	private Project project;
	private ManagedJar jar;
	private UUID id;
	private String resource, path, name;
	private HashMap<UUID, ManagedClass> dependencies, dependents;

	public ManagedClass(Project project, ManagedJar jar, UUID id, String resource, String path, String name) {
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

	public Project getProject() {
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
