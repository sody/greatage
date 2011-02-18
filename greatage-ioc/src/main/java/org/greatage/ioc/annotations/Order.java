/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks ordered objects and adds order id and order constraints. It is used in addition to {@link
 * org.greatage.ioc.annotations.Decorate} and {@link org.greatage.ioc.annotations.Intercept} annotations.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Order {

	/**
	 * This property defines unique order id for ordered object.
	 */
	String value();

	/**
	 * This property defines an array of order constraints for ordered object.
	 */
	String[] constraints() default {};
}
