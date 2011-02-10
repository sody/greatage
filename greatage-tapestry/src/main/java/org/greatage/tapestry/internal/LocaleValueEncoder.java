/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.internal;

import org.apache.tapestry5.ValueEncoder;
import org.greatage.util.I18nUtils;

import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LocaleValueEncoder implements ValueEncoder<Locale> {

	public String toClient(final Locale value) {
		return value != null ? value.toString() : null;
	}

	public Locale toValue(final String clientValue) {
		return I18nUtils.getLocale(clientValue);
	}

}
