/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.i18n;

import org.greatage.domain.GenericEntityImpl;

import java.util.*;

/**
 * @author Ivan Khalopik
 */
public class TranslatableEntityImpl extends GenericEntityImpl implements TranslatableEntity {
	private final Map<String, Translation> translations = new HashMap<String, Translation>();
	private final String type;
	private final List<String> variants;
	private String code;

	public TranslatableEntityImpl() {
		this.type = getClass().getName();
		this.variants = Collections.singletonList("description");
	}

	public TranslatableEntityImpl(String type, List<String> variants) {
		this.type = type;
		this.variants = variants;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Map<String, Translation> getTranslations() {
		return translations;
	}

	public void setTranslations(Map<String, Translation> translations) {
		this.translations.clear();
		this.translations.putAll(translations);
	}

	public String getTranslationType() {
		return type;
	}

	public String getTranslationCode() {
		return code;
	}

	public Collection<String> getTranslationVariants() {
		return variants;
	}

}
