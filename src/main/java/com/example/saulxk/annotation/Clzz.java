package com.example.saulxk.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Clzz {
	String value() default "";
	String name() default "";
}
