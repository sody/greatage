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
 * This annotation marks methods inside the IoC module as service decorate points that decorates services with specified
 * service id or service interface. Such methods can get as arguments other services, existing service
 * instance,collection, list and map for unordered, ordered and mapped configurations respectively.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
@Target(METHOD)
@Retention(RUNTIME)
@Documented
public @interface Decorate {

	/**
	 * This property defines service interface for decoration.
	 *
	 * @return service interface
	 */
	Class value();

	/**
	 * This property defines service unique id for decoration.
	 *
	 * @return service unique id
	 */
	String serviceId() default "";

}
