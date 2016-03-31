package com.error22.lychee.server.managed;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClassScanner extends ClassVisitor {
	private static Logger log = LogManager.getLogger();

	public ClassScanner() {
		super(Opcodes.ASM5);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		log.info("visit " + version + " " + access + " " + name + " " + signature + " " + superName + " "
				+ Arrays.toString(interfaces));
	}

	@Override
	public void visitSource(String source, String debug) {
		log.info("visitSource " + source + " " + debug);
	}

	@Override
	public MethodVisitor visitMethod(int paramInt, String paramString1, String paramString2, String paramString3,
			String[] paramArrayOfString) {
		log.info("visitMethod " + paramInt + " " + paramString1 + " " + paramString2 + " " + paramString3 + " "
				+ Arrays.toString(paramArrayOfString));
		return new MethodVisitor(Opcodes.ASM5) {
			@Override
			public void visitEnd() {
				log.info(" visit end!");
				super.visitEnd();
			}

			@Override
			public void visitParameter(String paramString, int paramInt) {
				log.info("visitParameter " + paramString + " " + paramInt);
				super.visitParameter(paramString, paramInt);
			}
		};
	}

}
