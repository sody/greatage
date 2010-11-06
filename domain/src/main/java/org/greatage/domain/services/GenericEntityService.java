/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.services;

import org.greatage.domain.GenericEntity;

/**
 * @author Ivan Khalopik
 */
public interface GenericEntityService<E extends GenericEntity>
		extends EntityService<Long, E> {
}
