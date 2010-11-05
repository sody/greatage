package org.greatage.domain.i18n;

import org.greatage.domain.repository.EntityCriteria;
import org.greatage.domain.repository.GenericEntityFilterProcessor;

/**
 * @author Ivan Khalopik
 */
public class TranslationFilterProcessor extends GenericEntityFilterProcessor<Translation, TranslationFilter> {

	public TranslationFilterProcessor() {
		super(Translation.class);
	}

	@Override
	protected void processFilter(EntityCriteria criteria, TranslationFilter filter) {
		if (filter.getType() != null) {
			criteria.add(criteria.getProperty(Translation.TYPE_PROPERTY).eq(filter.getType()));
		}
		if (filter.getLocale() != null) {
			criteria.add(criteria.getProperty(Translation.LOCALE_PROPERTY).eq(filter.getLocale()));
		}
		if (filter.getCodes() != null) {
			criteria.add(criteria.getProperty(Translation.CODE_PROPERTY).in(filter.getCodes()));
		}
		if (filter.getVariants() != null) {
			criteria.add(criteria.getProperty(Translation.VARIANT_PROPERTY).in(filter.getVariants()));
		}
	}
}