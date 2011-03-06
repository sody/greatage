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

package org.greatage.ioc.coerce;

/**
 * This class represents abstract {@link Coercion} implementation that defines source and target classes in its
 * constructor.
 *
 * @param <S> source type
 * @param <T> target type
 * @author Ivan Khalopik
 * @since 1.1
 */
public abstract class AbstractCoercion<S, T> implements Coercion<S, T> {
	private final Class<S> sourceClass;
	private final Class<T> targetClass;

	/**
	 * Creates new coercion instance with defined source and target classes.
	 *
	 * @param sourceClass source class, not null
	 * @param targetClass target class, not null
	 */
	protected AbstractCoercion(final Class<S> sourceClass, final Class<T> targetClass) {
		assert sourceClass != null;
		assert targetClass != null;

		this.sourceClass = sourceClass;
		this.targetClass = targetClass;
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<T> getTargetClass() {
		return targetClass;
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<S> getSourceClass() {
		return sourceClass;
	}
}
