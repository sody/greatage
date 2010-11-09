/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository;

import org.greatage.domain.GenericEntity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Deprecated
public interface GenericEntityFilter<E extends GenericEntity> extends EntityFilter<Long, E> {
}
