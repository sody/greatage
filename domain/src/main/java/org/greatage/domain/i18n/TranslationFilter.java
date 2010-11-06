/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.i18n;

import org.greatage.domain.repository.GenericEntityFilter;

import java.util.List;
import java.util.Locale;

/**
 * @author Ivan Khalopik
 */
public interface TranslationFilter extends GenericEntityFilter<Translation> {

	String getType();

	Locale getLocale();

	List<String> getCodes();

	List<String> getVariants();

}