/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Target(METHOD)
@Retention(RUNTIME)
@Documented
public @interface Decorate {

	Class value();

	String serviceId() default "";

}
