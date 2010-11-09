/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository.jpa;

import javax.persistence.EntityManager;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public interface JpaCallback<T> {

	T doInJpa(EntityManager em) throws Throwable;

}
