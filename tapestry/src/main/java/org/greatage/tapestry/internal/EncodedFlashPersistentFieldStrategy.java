/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.internal;

import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;
import org.apache.tapestry5.services.ValueEncoderSource;

/**
 * @author Ivan Khalopik
 */
public class EncodedFlashPersistentFieldStrategy extends EncodedSessionPersistentFieldStrategy {
	private static final String ENCODED_FLASH_PREFIX = "encoded-flash:";

	public EncodedFlashPersistentFieldStrategy(Request request, ValueEncoderSource valueEncoderSource, RealClassResolver realClassResolver) {
		super(ENCODED_FLASH_PREFIX, request, valueEncoderSource, realClassResolver);
	}

	@Override
	protected void didReadChange(Session session, String attributeName) {
		session.setAttribute(attributeName, null);
	}
}
