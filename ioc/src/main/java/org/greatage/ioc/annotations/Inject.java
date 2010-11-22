/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This annotation marks class fields and method parameters inside the IoC container as injected services with specified
 * unique id or service interface.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
public @interface Inject {

	/**
	 * This property defines service id for injection. If is empty service will be injected by its interface.
	 *
	 * @return service unique id
	 */
	String value() default "";

}
