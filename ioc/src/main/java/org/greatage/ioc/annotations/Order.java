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
 * This annotation marks ordered objects and adds order id and order constraints. It is used in addition to {@link
 * org.greatage.ioc.annotations.Decorate} and {@link org.greatage.ioc.annotations.Intercept} annotations.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
@Target(METHOD)
@Retention(RUNTIME)
@Documented
public @interface Order {

	/**
	 * This property defines unique order id for ordered object.
	 *
	 * @return unique order id
	 */
	String value();

	/**
	 * This property defines an array of order constraints for ordered object.
	 *
	 * @return array of order constraints
	 */
	String[] constraints() default {};

}
