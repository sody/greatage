/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.i18n;

import org.greatage.domain.GenericEntityImpl;

import java.util.Locale;

/**
 * @author Ivan Khalopik
 */
public class TranslationImpl extends GenericEntityImpl implements Translation {
	private String type;
	private String code;
	private String variant;
	private Locale locale;
	private String value;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getVariant() {
		return variant;
	}

	public void setVariant(String variant) {
		this.variant = variant;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
