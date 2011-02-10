/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This annotation marks module class inside the IoC container as definition for child modules.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface Dependency {

	/**
	 * This property defines child modules.
	 *
	 * @return classes of child modules
	 */
	Class[] value();

}
