/*
 * Copyright 2011 Ivan Khalopik
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

package org.greatage.ioc.proxy;

import java.lang.reflect.Method;

/**
 * This class represent method invocation in environment of {@link Interceptor}. It provides information about method
 * name, its signature and annotations.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Invocation {

	Object getTarget();

	Method getMethod();

	/**
	 * Proceeds an underlying method invocation with specified parameters.
	 *
	 * @param parameters method invocation parameters
	 * @return method invocation return result
	 * @throws Throwable when error occurs during invocation
	 */
	Object proceed(Object... parameters) throws Throwable;
}
