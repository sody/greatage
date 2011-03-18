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
import java.util.List;

/**
 * This class represents implementation of invocation handler for javassist proxy objects.
 *
 * @param <T> object type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JavassistInvocationHandler<T> extends AbstractInvocationHandler<T> {

	/**
	 * Creates new instance of invocation handler for javassist proxy objects.
	 *
	 * @param builder	  object builder
	 * @param interceptors method interceptors
	 */
	public JavassistInvocationHandler(final ObjectBuilder<T> builder, final List<Interceptor> interceptors) {
		super(builder, interceptors);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getDelegate() {
		return super.getDelegate();
	}

	/**
	 * Invokes specified method with specified parameters on delegate instance
	 *
	 * @param method	 interface method
	 * @param parameters invocation parameters
	 * @return delegated from invocation return object
	 * @throws Throwable if some problems occurs while invoking method
	 */
	@SuppressWarnings("UnusedDeclaration")
	public Object invoke(final Method method, final Object... parameters) throws Throwable {
		return getInvocation(method).proceed(parameters);
	}
}
