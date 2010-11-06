/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.i18n;

import org.greatage.domain.GenericEntity;

import java.util.Locale;

/**
 * @author Ivan Khalopik
 */
public interface Translation extends GenericEntity {
	public static final String TYPE_PROPERTY = "type";
	public static final String CODE_PROPERTY = "code";
	public static final String VARIANT_PROPERTY = "variant";
	public static final String LOCALE_PROPERTY = "locale";
	public static final String VALUE_PROPERTY = "value";

	String getType();

	void setType(String type);

	String getCode();

	void setCode(String code);

	String getVariant();

	void setVariant(String variant);

	Locale getLocale();

	void setLocale(Locale locale);

	String getValue();

	void setValue(String value);

}
