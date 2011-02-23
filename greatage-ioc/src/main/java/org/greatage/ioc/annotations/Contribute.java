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
 * This annotation marks methods inside the IoC module as service contribute points that contributes to service
 * configuration with specified service id or service interface. Such methods can get as arguments other services and
 * also {@link org.greatage.ioc.Configuration}, {@link org.greatage.ioc.OrderedConfiguration} and {@link
 * org.greatage.ioc.MappedConfiguration} for unordered, ordered and mapped service configuration respectively.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Contribute {

	/**
	 * This property defines service alias (class name) that represents service unique id.
	 */
	Class value() default Void.class;

	/**
	 * This property defines service unique id.
	 */
	String id() default "";

	/**
	 * This property defines service interface for contribution.
	 */
	Class service() default Void.class;
}
