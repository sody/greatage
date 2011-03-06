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

package org.greatage.ioc.proxy;

/**
 * This class represent method advice that is used to advice object proxy methods with some additional functionality.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface MethodAdvice {

	/**
	 * Advices method invocation. It can override method or add some additional functionality to method.
	 *
	 * @param invocation method invocation
	 * @param parameters invocation parameters
	 * @return method return result
	 * @throws Throwable when error occurs during invocation or may be added by this method advice
	 */
	Object advice(Invocation invocation, Object... parameters) throws Throwable;
}
