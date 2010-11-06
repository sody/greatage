/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.i18n;

import org.greatage.domain.GenericEntity;

import java.util.Map;

/**
 * @author Ivan Khalopik
 */
public interface TranslatableEntity extends GenericEntity, Translatable {
	public static final String CODE_PROPERTY = "code";

	String getCode();

	void setCode(String code);

	Map<String, Translation> getTranslations();

	void setTranslations(Map<String, Translation> translations);

}
