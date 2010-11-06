/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.services;

import org.greatage.domain.GenericEntity;
import org.greatage.domain.repository.EntityRepository;
import org.greatage.domain.repository.GenericEntityQuery;

/**
 * @author Ivan Khalopik
 */
public class GenericEntityServiceImpl<E extends GenericEntity, Q extends GenericEntityQuery<E, Q>>
		extends EntityServiceImpl<Long, E, Q>
		implements GenericEntityService<E> {

	public GenericEntityServiceImpl(EntityRepository repository, Class<E> entityClass) {
		super(repository, entityClass);
	}

	public GenericEntityServiceImpl(EntityRepository repository, Class<E> entityClass, Class<Q> queryClass) {
		super(repository, entityClass, queryClass);
	}

	public GenericEntityServiceImpl(EntityRepository repository, Class<E> entityClass, Class<Q> queryClass, String entityName) {
		super(repository, entityClass, queryClass, entityName);
	}

}
