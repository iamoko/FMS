package org.pahappa.systems.core.settings;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to be added to auto wired beans that contain migrations for auto
 * detection
 * 
 * @author Eng.Ivan
 *
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface CustomMigration {

}
