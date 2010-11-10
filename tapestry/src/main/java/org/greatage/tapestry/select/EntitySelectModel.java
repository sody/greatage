/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.select;

import org.greatage.domain.Entity;
import org.greatage.domain.EntityService;

import java.io.Serializable;
import java.util.List;

/**
 * Needs ga:ga-core dependency
 *
 * @author Ivan Khalopik
 */
public class EntitySelectModel<PK extends Serializable, E extends Entity<PK>>
		extends LazySelectModel<E> {

	private final EntityService<PK, E> entityService;

	public EntitySelectModel(EntityService<PK, E> entityService) {
		this.entityService = entityService; //todo: add defence support
	}

	@Override
	public List<E> getValues() {
		return entityService.getEntities();
	}
}
