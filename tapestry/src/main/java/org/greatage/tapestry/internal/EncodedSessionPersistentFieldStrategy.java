/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
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
