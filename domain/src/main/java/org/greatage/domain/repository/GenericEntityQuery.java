/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository;

import org.greatage.domain.GenericEntity;

/**
 * @author Ivan Khalopik
 */
public class GenericEntityQuery<E extends GenericEntity, Q extends GenericEntityQuery<E, Q>>
		extends EntityQuery<Long, E, Q>
		implements GenericEntityFilter<E> {

	public GenericEntityQuery(Class<E> entityClass) {
		super(entityClass);
	}
}
