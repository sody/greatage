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

package org.greatage.ioc.coerce;

import org.greatage.util.CollectionUtils;
import org.greatage.util.CompositeKey;

import java.util.Collection;
import java.util.Map;

/**
 * This class represents default {@link TypeCoercer} implementation that are configurable with {@link CoercionProvider}
 * instances.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public class TypeCoercerImpl implements TypeCoercer {
	private final Collection<CoercionProvider> coercionProviders;
	private final Map<CompositeKey, Coercion> cache = CollectionUtils.newConcurrentMap();

	/**
	 * Creates new instance of type coercer configured with specified coercion providers
	 *
	 * @param coercionProviders coercion providers
	 */
	public TypeCoercerImpl(final Collection<CoercionProvider> coercionProviders) {
		assert coercionProviders != null;

		this.coercionProviders = coercionProviders;
	}

	/**
	 * {@inheritDoc}
	 */
	public <S, T> T coerce(final S input, final Class<T> targetClass) {
		assert targetClass != null;

		if (input == null || targetClass.isAssignableFrom(input.getClass())) {
			return targetClass.cast(input);
		}

		@SuppressWarnings("unchecked")
		final Class<S> sourceClass = (Class<S>) input.getClass();

		final Coercion<S, T> coercion = getCoercion(sourceClass, targetClass);
		if (coercion == null) {
			throw new CoerceException(input, targetClass);
		}
		return coercion.coerce(input);
	}

	/**
	 * {@inheritDoc} It also caches results.
	 */
	@SuppressWarnings("unchecked")
	public <S, T> Coercion<S, T> getCoercion(final Class<S> sourceClass, final Class<T> targetClass) {
		final CompositeKey key = new CompositeKey(sourceClass, targetClass);
		if (cache.containsKey(key)) {
			return cache.get(key);
		}

		for (CoercionProvider provider : coercionProviders) {
			final Coercion<S, T> coercion = provider.getCoercion(sourceClass, targetClass);
			if (coercion != null) {
				cache.put(key, coercion);
				return coercion;
			}
		}
		return null;
	}
}
