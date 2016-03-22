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
import org.objectweb.asm.commons.Remapper;

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

			

			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

			cr.accept(new ClassRemapper(cw, new Remapper() {

				@Override
				public String mapMethodName(String paramString1, String paramString2, String paramString3) {
					log.info("mapMethodName " + paramString1 + " " + paramString2 + " " + paramString3);

					return super.mapMethodName(paramString1, paramString2, paramString3);
				}

				@Override
				public String mapInvokeDynamicMethodName(String paramString1, String paramString2) {
					log.info("mapInvokeDynamicMethodName " + paramString1 + " " + paramString2);

					return super.mapInvokeDynamicMethodName(paramString1, paramString2);
				}

				@Override
				public String mapFieldName(String paramString1, String paramString2, String paramString3) {
					log.info("mapFieldName " + paramString1 + " " + paramString2 + " " + paramString3);
					return super.mapFieldName(paramString1, paramString2, paramString3);
				}

				@Override
				public String map(String paramString) {
					log.info("map " + paramString);
					return super.map(paramString);
				}

			}) {
				@Override
				public void visit(int paramInt1, int paramInt2, String paramString1, String paramString2,
						String paramString3, String[] paramArrayOfString) {
					super.visit(paramInt1, paramInt2, paramString1, paramString2, paramString3, paramArrayOfString);
				}

				@Override
				public AnnotationVisitor visitAnnotation(String paramString, boolean paramBoolean) {
					log.info("visitAnnotation " + paramString + " " + paramBoolean);
					return super.visitAnnotation(paramString, paramBoolean);
				}

				@Override
				public AnnotationVisitor visitTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString,
						boolean paramBoolean) {
					log.info("visitTypeAnnotation " + paramInt + " " + paramTypePath + " " + paramString + " "
							+ paramBoolean);
					return super.visitTypeAnnotation(paramInt, paramTypePath, paramString, paramBoolean);
				}

				@Override
				protected AnnotationVisitor createAnnotationRemapper(AnnotationVisitor paramAnnotationVisitor) {
					return new AnnotationRemapper(paramAnnotationVisitor, remapper) {
						@Override
						public void visit(String paramString, Object paramObject) {
							log.info("visit " + paramString + " " + paramObject + " " + (paramObject.getClass()));
							super.visit(paramString, paramObject);
						}

						@Override
						public void visitEnum(String paramString1, String paramString2, String paramString3) {
							log.info("visitEnum " + paramString1 + " " + paramString2 + " " + paramString3);
							super.visitEnum(paramString1, paramString2, paramString3);
						}

					};
				}
			}, 0);
			
			StringWriter sw = new StringWriter();

			DecompilerSettings settings = new DecompilerSettings();
			settings.setFormattingOptions(JavaFormattingOptions.createDefault());
			settings.setTypeLoader(new ITypeLoader() {
				
				@Override
				public boolean tryLoadType(String paramString, Buffer paramBuffer) {
					log.info("tryLoadType "+paramString+" "+paramBuffer);
					byte[] d = cw.toByteArray();
					if(paramString.equals(resource)){
						paramBuffer.reset(d.length);
						paramBuffer.putByteArray(d, 0, d.length);
						paramBuffer.position(0);
						return true;
					}
					
					return false;
				}
			});
			
			Decompiler.decompile(resource, new PlainTextOutput(sw), settings);
			
			log.info(" "+sw.toString());

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

}
