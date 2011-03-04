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
 * This class represents {@link Invocation} proxy implementation that adds method advices logic to invocation delegate.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AdvisedInvocation implements Invocation {
	private final Invocation delegate;
	private final MethodAdvice advice;

	/**
	 * Creates new instance of invocation proxy that adds method advices logic to invocation delegate.
	 *
	 * @param delegate invocation delegate
	 * @param advice   invocation method advice, can be null
	 */
	public AdvisedInvocation(final Invocation delegate, final MethodAdvice advice) {
		this.delegate = delegate;
		this.advice = advice;
	}

	/**
	 * {@inheritDoc}
	 */
	public Method getMethod() {
		return delegate.getMethod();
	}

	/**
	 * {@inheritDoc}
	 */
	public Method getRealMethod() {
		return delegate.getRealMethod();
	}

	/**
	 * {@inheritDoc} Adds method advices logic to invocation delegate.
	 */
	public Object proceed(final Object... parameters) throws Throwable {
		return advice.advice(delegate, parameters);
	}
}
