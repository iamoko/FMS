package org.pahappa.systems.models.permissions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CustomPermAnnotation {

	String name();

	String description() default "Value not set!";
}
