/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.i18n;

import org.greatage.domain.annotations.Transactional;
import org.greatage.domain.repository.EntityRepository;
import org.greatage.domain.services.GenericEntityServiceImpl;

import java.util.*;

/**
 * @author Ivan Khalopik
 */
public class TranslationServiceImpl
		extends GenericEntityServiceImpl<Translation, TranslationQuery>
		implements TranslationService {

	private Translator translator;

	public TranslationServiceImpl(EntityRepository repository) {
		super(repository, Translation.class, TranslationQuery.class, "translation");
	}

	public void setTranslator(Translator translator) {
		this.translator = translator;
	}

	public Map<String, String> getMessages(Translatable translatable, Locale locale) {
		final List<Translation> translations = getEntities(translatable, locale);
		final Map<String, String> result = new HashMap<String, String>();
		for (Translation translation : translations) {
			result.put(translation.getVariant(), translation.getValue());
		}
		return result;
	}

	public Map<String, Translation> getTranslations(Translatable translatable, Locale locale) {
		final List<Translation> translations = getEntities(translatable, locale);
		final Map<String, Translation> mapped = new HashMap<String, Translation>();
		for (Translation translation : translations) {
			mapped.put(translation.getVariant(), translation);
		}

		final Map<String, Translation> result = new HashMap<String, Translation>();
		for (String variant : translatable.getTranslationVariants()) {
			if (mapped.containsKey(variant)) {
				result.put(variant, mapped.get(variant));
			} else {
				final Translation translation = create();
				translation.setLocale(locale);
				translation.setVariant(variant);
				result.put(variant, translation);
			}
		}
		return result;
	}

	@Transactional
	public void saveTranslations(Translatable translatable, Map<String, Translation> translations) {
		for (Translation translation : translations.values()) {
			translation.setType(translatable.getTranslationType());
			translation.setCode(translatable.getTranslationCode());
			saveOrUpdate(translation);
		}
		updateTranslatorCache(translatable, translations);
	}

	private List<Translation> getEntities(Translatable translatable, Locale locale) {
		if (translatable.getTranslationCode() == null) {
			return Collections.emptyList();
		}
		return createQuery()
				.setType(translatable.getTranslationType())
				.setLocale(locale)
				.addCode(translatable.getTranslationCode())
				.list(createDefaultPagination());
	}

	private void updateTranslatorCache(Translatable translatable, Map<String, Translation> translations) {
		if (translator != null && !translations.isEmpty()) {
			final Locale locale = translations.values().iterator().next().getLocale();
			final Map<String, String> messages = new HashMap<String, String>();
			for (Translation translation : translations.values()) {
				messages.put(translation.getVariant(), translation.getValue());
			}
			translator.updateCache(translatable, locale, messages);
		}
	}
}
