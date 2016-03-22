package com.example;

public class ExampleClass {
	
	
	public static String example(String[] args){
		return "This will be replaced with patches!";
	}
	
	public static void main(String[] args) {
		System.out.println("This is a test input");
		
		System.out.println("Name: "+ExampleClass.class.getName());
		System.out.println("Other: "+OtherExample.class.getName());
		
		System.out.println("Call: "+example(args));
	}
	
}
