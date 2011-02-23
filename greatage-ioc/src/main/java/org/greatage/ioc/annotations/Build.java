/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.annotations;

import org.greatage.ioc.scope.ScopeConstants;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks methods inside the IoC module as service build points that will build service instances with
 * specified unique id, scope and override option. Such methods can get as arguments other services, collection, list
 * and map for unordered, ordered and mapped configurations respectively.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Build {

	/**
	 * This property defines service alias (class name) that represents service unique id.
	 */
	Class value() default Void.class;

	/**
	 * This property defines service unique id.
	 */
	String id() default "";

	/**
	 * This property defines if service definition will be overridden or not.
	 */
	boolean override() default false;

	/**
	 * This property defines service scope. This scope must present inside {@link org.greatage.ioc.scope.ScopeManager}
	 * configuration. The default scope is global.
	 */
	String scope() default ScopeConstants.GLOBAL;
}
