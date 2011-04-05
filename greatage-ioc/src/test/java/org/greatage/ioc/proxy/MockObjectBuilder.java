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

import org.greatage.util.DescriptionBuilder;
import org.greatage.util.ReflectionUtils;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class MockObjectBuilder<T> implements ObjectBuilder<T> {
	private final Class<T> interfaceClass;
	private final Class<? extends T> implementationClass;
	private final List<Interceptor> interceptors;
	private final Object[] constructionParameters;

	public MockObjectBuilder(final Class<T> interfaceClass,
							 final Class<? extends T> implementationClass,
							 final List<Interceptor> interceptors,
							 final Object... constructionParameters) {
		this.interfaceClass = interfaceClass;
		this.implementationClass = implementationClass;
		this.interceptors = interceptors;
		this.constructionParameters = constructionParameters;
	}

	public Class<T> getObjectClass() {
		return interfaceClass;
	}

	public List<Interceptor> getInterceptors() {
		return interceptors;
	}

	public T build() {
		return ReflectionUtils.newInstance(implementationClass, constructionParameters);
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("interface", interfaceClass);
		builder.append("implementation", implementationClass);
		return builder.toString();
	}
}
