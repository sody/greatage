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

package org.greatage.ioc.scope;

/**
 * This class represents all base pre-defined scope identifiers.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class ScopeConstants {

	/**
	 * Identifier for global scope. It is used for services that have the same state for whole application.
	 */
	public static final String GLOBAL = "global";

	/**
	 * Identifier for prototype scope. It is used for services that have different state for all points where it is
	 * accessed.
	 */
	public static final String PROTOTYPE = "prototype";

	/**
	 * Identifier for thread scope. It is used for services that have the same state inside one application thread.
	 */
	public static final String THREAD = "thread";
}
