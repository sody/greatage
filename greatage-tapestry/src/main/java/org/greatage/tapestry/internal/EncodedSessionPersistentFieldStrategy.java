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

package org.greatage.tapestry.internal;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.internal.services.AbstractSessionPersistentFieldStrategy;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ValueEncoderSource;

/**
 * @author Ivan Khalopik
 */
public class EncodedSessionPersistentFieldStrategy extends AbstractSessionPersistentFieldStrategy {
	private static final String ENCODED_SESSION_PREFIX = "encoded-session:";

	private final ValueEncoderSource valueEncoderSource;
	private final RealClassResolver realClassResolver;

	public EncodedSessionPersistentFieldStrategy(Request request, ValueEncoderSource valueEncoderSource, RealClassResolver realClassResolver) {
		this(ENCODED_SESSION_PREFIX, request, valueEncoderSource, realClassResolver);
	}

	protected EncodedSessionPersistentFieldStrategy(String prefix, Request request, ValueEncoderSource valueEncoderSource, RealClassResolver realClassResolver) {
		super(prefix, request);
		this.valueEncoderSource = valueEncoderSource;
		this.realClassResolver = realClassResolver;
	}

	@Override
	@SuppressWarnings({"unchecked"})
	protected Object convertApplicationValueToPersisted(Object newValue) {
		if (newValue != null) {
			final Class<?> type = getRealClass(newValue.getClass());
			final ValueEncoder valueEncoder = getValueEncoder(type);
			return new PersistedValue(type, valueEncoder.toClient(newValue));
		}
		return null;
	}

	@Override
	@SuppressWarnings({"unchecked"})
	protected Object convertPersistedToApplicationValue(Object persistedValue) {
		if (persistedValue != null) {
			final PersistedValue value = (PersistedValue) persistedValue;
			final ValueEncoder valueEncoder = getValueEncoder(value.getValueClass());
			return valueEncoder.toValue(value.getValue());
		}
		return null;
	}

	protected <T> Class<T> getRealClass(Class<T> clazz) {
		return realClassResolver.getRealClass(clazz);
	}

	protected <T> ValueEncoder<T> getValueEncoder(Class<T> clazz) {
		return valueEncoderSource.getValueEncoder(clazz);
	}
}
