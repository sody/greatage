/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.i18n;

import org.greatage.domain.repository.GenericEntityFilter;

/**
 * @author Ivan Khalopik
 */
public interface TranslatableEntityFilter<E extends TranslatableEntity>
		extends GenericEntityFilter<E> {

	String getCode();

}
