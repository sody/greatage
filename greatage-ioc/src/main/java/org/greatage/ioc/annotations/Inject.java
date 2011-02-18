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
 * This annotation marks class fields and method parameters inside the IoC container as injected services with specified
 * unique id or service interface.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inject {

	/**
	 * This property defines service id for injection. If is empty service will be injected by its interface.
	 */
	String value() default "";
}
