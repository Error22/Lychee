package com.error22.lychee.server.managed;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClassScanner extends ClassVisitor{
	private static Logger log = LogManager.getLogger();
	
	public ClassScanner() {
		super(Opcodes.ASM5);
	}
	
	@Override
	public void visit(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3,
			String[] paramArrayOfString) {
		log.info("visit "+paramInt1+" "+paramInt2+" "+paramString1+" "+paramString2+" "+paramString3+" "+Arrays.toString(paramArrayOfString));
	}
	
	@Override
	public MethodVisitor visitMethod(int paramInt, String paramString1, String paramString2, String paramString3,
			String[] paramArrayOfString) {
		log.info("visitMethod "+paramInt+" "+paramString1+" "+paramString2+" "+paramString3+" "+Arrays.toString(paramArrayOfString));
		return new MethodVisitor(Opcodes.ASM5) {
			@Override
			public void visitEnd() {
				log.info(" visit end!");
				super.visitEnd();
			}
			
			@Override
			public void visitParameter(String paramString, int paramInt) {
				log.info("visitParameter "+paramString+ " "+paramInt);
				super.visitParameter(paramString, paramInt);
			}
		};
	}
	

}
