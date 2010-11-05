/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository.hibernate;

import org.hibernate.Criteria;

/**
 * This interface represents criteria callback method that will be executed inside the criteria execution.
 *
 * @author Ivan Khalopik
 * @see HibernateEntityRepository
 */
public interface CriteriaCallback<R> {

	/**
	 * Executes some logic for returning values from criteria.
	 *
	 * @param criteria hibernate criteria holder
	 * @return criteria execution result
	 */
	R doInCriteria(Criteria criteria);

}
