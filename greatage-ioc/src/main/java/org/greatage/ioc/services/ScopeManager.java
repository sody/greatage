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

package org.greatage.ioc.services;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * This interface represents utility service that obtains scope instances by their name.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ScopeManager {

	/**
	 * Obtains scope instance by its name.
	 *
	 * @param scope scope name
	 * @return scope instance correspondent to specified scope name or null if doesn't exist
	 */
	Scope getScope(Class<? extends Annotation> scope); //todo: get by marker?

	Collection<Scope> getScopes();
}
