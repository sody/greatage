package org.greatage.domain.i18n;

import org.greatage.cache.Cache;
import org.greatage.cache.MultiKey;
import org.greatage.cache.SimpleCache;
import org.greatage.util.I18nUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Ivan Khalopik
 */
public class TranslatorImpl implements Translator {
	private final TranslationService translationService;
	//todo: add settings to cache
	private final Cache<MultiKey, Map<String, String>> cache = new SimpleCache<MultiKey, Map<String, String>>();

	public TranslatorImpl(TranslationService translationService) {
		this.translationService = translationService;
	}

	public Map<String, String> translate(Translatable translatable) {
		return translate(translatable, I18nUtils.ROOT_LOCALE);
	}

	public Map<String, String> translate(Translatable translatable, Locale locale) {
		final Map<String, String> result = new HashMap<String, String>();
		for (Locale candidateLocale : I18nUtils.getCandidateLocales(locale)) {
			final Map<String, String> messages = getFromCache(translatable, candidateLocale);
			if (merge(translatable, result, messages)) {
				return result;
			}
		}
		return result;
	}

	public void updateCache(Translatable translatable, Locale locale, Map<String, String> messages) {
		final MultiKey key = createKey(translatable, locale);
		cache.put(key, messages);
	}

	private boolean merge(Translatable translatable, Map<String, String> dst, Map<String, String> src) {
		boolean allFound = true;
		for (String variant : translatable.getTranslationVariants()) {
			if (!dst.containsKey(variant)) {
				if (src.containsKey(variant)) {
					dst.put(variant, src.get(variant));
				} else {
					allFound = false;
				}
			}
		}
		return allFound;
	}

	private Map<String, String> getFromCache(Translatable translatable, Locale locale) {
		final MultiKey key = createKey(translatable, locale);
		if (!cache.contains(key)) {
			final Map<String, String> messages = translationService.getMessages(translatable, locale);
			cache.put(key, messages);
		}
		return cache.get(key);
	}

	private MultiKey createKey(Translatable translatable, Locale locale) {
		return new MultiKey(translatable.getTranslationType(), translatable.getTranslationCode(), locale);
	}
}
