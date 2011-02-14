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
 * This annotation marks methods inside the IoC module as service advice points that will advice service methods with
 * specified unique id or service interface. Such methods can get as arguments other services, collection, list and map
 * for unordered, ordered and mapped configurations respectively.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Intercept {

	/**
	 * This property defines service interface for intercepting.
	 *
	 * @return service interface
	 */
	Class value();

	/**
	 * This property defines service unique id for intercepting.
	 *
	 * @return service unique id
	 */
	String serviceId() default "";
}
