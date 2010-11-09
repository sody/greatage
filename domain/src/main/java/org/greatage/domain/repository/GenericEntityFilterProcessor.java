/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository;

import org.greatage.domain.Entity;
import org.greatage.domain.GenericEntity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Deprecated
public abstract class GenericEntityFilterProcessor<E extends GenericEntity, F extends GenericEntityFilter<E>>
		extends AbstractFilterProcessor<Long, E, F> {
	/**
	 * Constructor with supported entity class initialization.
	 *
	 * @param supportedEntityClass supported entity class
	 */
	protected GenericEntityFilterProcessor(Class<? extends Entity> supportedEntityClass) {
		super(supportedEntityClass);
	}
}
