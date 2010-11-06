/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.i18n;

import org.greatage.domain.repository.GenericEntityQuery;

/**
 * @author Ivan Khalopik
 */
public class TranslatableEntityQuery<E extends TranslatableEntity, Q extends TranslatableEntityQuery<E, Q>>
		extends GenericEntityQuery<E, Q>
		implements TranslatableEntityFilter<E> {

	private String code;

	public TranslatableEntityQuery(Class<E> entityClass) {
		super(entityClass);
	}

	public String getCode() {
		return code;
	}

	public Q setCode(String code) {
		this.code = code;
		return query();
	}
}
