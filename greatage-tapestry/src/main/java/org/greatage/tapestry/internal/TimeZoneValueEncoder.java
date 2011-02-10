/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.internal;

import org.apache.tapestry5.ValueEncoder;

import java.util.TimeZone;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TimeZoneValueEncoder implements ValueEncoder<TimeZone> {

	public String toClient(final TimeZone value) {
		return value != null ? value.getID() : null;
	}

	public TimeZone toValue(final String clientValue) {
		return clientValue != null ? TimeZone.getTimeZone(clientValue) : null;
	}

}
