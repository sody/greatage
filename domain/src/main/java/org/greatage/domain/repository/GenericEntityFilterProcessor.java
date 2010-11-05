package org.greatage.domain.repository;

import org.greatage.domain.Entity;
import org.greatage.domain.GenericEntity;

/**
 * @author Ivan Khalopik
 */
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
