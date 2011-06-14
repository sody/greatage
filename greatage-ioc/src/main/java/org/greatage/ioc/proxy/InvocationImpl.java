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

import java.lang.reflect.Method;

/**
 * This class represents {@link Invocation} implementation that is based on configured instance method invocation.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class InvocationImpl implements Invocation {
	private final Object target;
	private final Method method;

	/**
	 * Creates new instance of method invocation with defined target instance and invocation method.
	 *
	 * @param target object instance
	 * @param method invocation method
	 */
	InvocationImpl(final Object target, final Method method) {
		this.target = target;
		this.method = method;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getTarget() {
		return target;
	}

	/**
	 * {@inheritDoc}
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object proceed(final Object... parameters) throws Throwable {
		return method.invoke(target, parameters);
	}

	@Override
	public int hashCode() {
		return method.hashCode();
	}

	@Override
	public boolean equals(final Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof Invocation)) {
			return false;
		}
		final Invocation invocation = (Invocation) object;
		return method.equals(invocation.getMethod());
	}
}
