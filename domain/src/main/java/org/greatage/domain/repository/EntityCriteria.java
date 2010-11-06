/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository;

import org.greatage.domain.Pagination;

/**
 * @author Ivan Khalopik
 */
public interface EntityCriteria {

	String getAlias();

	EntityCriteria root();

	EntityCriteria getCriteria(String path);

	EntityProperty getProperty(String path);

	void add(EntityCriterion criterion);

	void setPagination(Pagination pagination);

}
