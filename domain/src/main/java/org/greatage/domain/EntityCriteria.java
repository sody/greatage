/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface EntityCriteria {

	String getAlias();

	EntityCriteria root();

	EntityCriteria getCriteria(String path);

	EntityProperty getProperty(String path);

	void add(EntityCriterion criterion);

	void setPagination(Pagination pagination);

}
