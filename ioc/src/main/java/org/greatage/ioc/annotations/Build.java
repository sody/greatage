/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.annotations;

import org.greatage.ioc.ScopeConstants;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This annotation marks methods inside the IoC module as service build points that will build service instances with
 * specified unique id, scope and override option. Such methods can get as arguments other services, collection, list
 * and map for unordered, ordered and mapped configurations respectively.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
@Target(METHOD)
@Retention(RUNTIME)
@Documented
public @interface Build {

	/**
	 * This property defines service unique id. If it is empty id will be generated from simplified service interface
	 * name.
	 *
	 * @return service unique id
	 */
	String value() default "";

	/**
	 * This property defines if service definition will be overridden or not.
	 *
	 * @return true if this is service override definition, false otherwise
	 */
	boolean override() default false;

	/**
	 * This property defines service scope. This scope must present inside {@link org.greatage.ioc.scope.ScopeManager}
	 * configuration. The default scope is global.
	 *
	 * @return service scope
	 */
	String scope() default ScopeConstants.GLOBAL;

}
