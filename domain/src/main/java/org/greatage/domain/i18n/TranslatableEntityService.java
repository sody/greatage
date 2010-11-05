package org.greatage.domain.i18n;

import org.greatage.domain.services.GenericEntityService;

import java.util.Locale;

/**
 * @author Ivan Khalopik
 */
public interface TranslatableEntityService<E extends TranslatableEntity>
		extends GenericEntityService<E> {

	E get(Long pk, Locale locale);

}
