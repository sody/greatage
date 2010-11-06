/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.i18n;

import org.greatage.domain.repository.GenericEntityQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Ivan Khalopik
 */
public class TranslationQuery
		extends GenericEntityQuery<Translation, TranslationQuery>
		implements TranslationFilter {

	private String type;
	private Locale locale;
	private List<String> codes;
	private List<String> variants;

	public TranslationQuery() {
		super(Translation.class);
	}

	public String getType() {
		return type;
	}

	public Locale getLocale() {
		return locale;
	}

	public List<String> getCodes() {
		return codes;
	}

	public List<String> getVariants() {
		return variants;
	}

	public TranslationQuery setType(String type) {
		this.type = type;
		return query();
	}

	public TranslationQuery setLocale(Locale locale) {
		this.locale = locale;
		return query();
	}

	public TranslationQuery addCode(String code) {
		if (codes == null) {
			codes = new ArrayList<String>();
		}
		codes.add(code);
		return query();
	}

	public TranslationQuery addVariant(String variant) {
		if (variants == null) {
			variants = new ArrayList<String>();
		}
		variants.add(variant);
		return query();
	}
}
