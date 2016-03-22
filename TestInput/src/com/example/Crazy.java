package com.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Crazy {
	
	public String name();
	
	public String day();
	
	public Class<? extends ExampleClass> classThing();
	
	public SomeEnum cool();
}
