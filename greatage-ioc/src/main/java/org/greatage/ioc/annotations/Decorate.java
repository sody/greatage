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
 * This annotation marks methods inside the IoC module as service decorate points that decorates services with specified
 * service id or service interface. Such methods can get as arguments other services, existing service
 * instance,collection, list and map for unordered, ordered and mapped configurations respectively.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Decorate {

	/**
	 * This property defines service interface for decoration.
	 */
	Class value();

	/**
	 * This property defines service unique id for decoration.
	 */
	String serviceId() default "";
}
