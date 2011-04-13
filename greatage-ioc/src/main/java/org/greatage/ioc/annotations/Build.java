/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
 * @since 1.1
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Build {

	Class value() default void.class;

	/**
	 * This property defines if service definition will be overridden or not.
	 */
	boolean override() default false;

	boolean eager() default false;

	/**
	 * This property defines service scope. This scope must present inside {@link org.greatage.ioc.scope.ScopeManager}
	 * configuration. The default scope is global.
	 */
	String scope() default ScopeConstants.GLOBAL;
}
