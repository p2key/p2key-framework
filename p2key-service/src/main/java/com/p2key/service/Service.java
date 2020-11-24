package com.p2key.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Service {
	public enum Type {
		   PUBLIC, PRIVATE, PROTECTED
		}
	
	Type type() default Type.PROTECTED;
	
	String name() default "";
	
}
