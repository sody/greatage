/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.i18n;

import java.util.Locale;
import java.util.Map;

/**
 * @author Ivan Khalopik
 */
public interface Translator {

	Map<String, String> translate(Translatable translatable);

	Map<String, String> translate(Translatable translatable, Locale locale);

	void updateCache(Translatable translatable, Locale locale, Map<String, String> messages);

}