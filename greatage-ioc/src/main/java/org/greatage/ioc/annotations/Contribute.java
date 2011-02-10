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
 * This annotation marks methods inside the IoC module as service contribute points that contributes to service
 * configuration with specified service id or service interface. Such methods can get as arguments other services and
 * also {@link org.greatage.ioc.Configuration}, {@link org.greatage.ioc.OrderedConfiguration} and {@link
 * org.greatage.ioc.MappedConfiguration} for unordered, ordered and mapped service configuration respectively.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
@Target(METHOD)
@Retention(RUNTIME)
@Documented
public @interface Contribute {

	/**
	 * This property defines service interface for contribution.
	 *
	 * @return service interface
	 */
	Class value();

	/**
	 * This property defines service unique id for contribution.
	 *
	 * @return service unique id
	 */
	String serviceId() default "";

}
