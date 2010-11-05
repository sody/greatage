package org.greatage.domain.i18n;

import org.greatage.domain.services.GenericEntityService;

import java.util.Locale;
import java.util.Map;

/**
 * @author Ivan Khalopik
 */
public interface TranslationService extends GenericEntityService<Translation> {

	Map<String, String> getMessages(Translatable translatable, Locale locale);

	Map<String, Translation> getTranslations(Translatable translatable, Locale locale);

	void saveTranslations(Translatable translatable, Map<String, Translation> translations);


}
