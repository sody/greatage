package org.greatage.domain.repository;

import java.util.Collection;

/**
 * @author Ivan Khalopik
 */
public interface EntityProperty {

	String getProperty();

	EntityCriterion in(Collection values);

	EntityCriterion in(Object[] values);

	EntityCriterion like(Object value);

	EntityCriterion eq(Object value);

	EntityCriterion ne(Object value);

	EntityCriterion gt(Object value);

	EntityCriterion lt(Object value);

	EntityCriterion le(Object value);

	EntityCriterion ge(Object value);

	EntityCriterion between(Object min, Object max);

	EntityCriterion eqProperty(EntityProperty other);

	EntityCriterion neProperty(EntityProperty other);

	EntityCriterion leProperty(EntityProperty other);

	EntityCriterion geProperty(EntityProperty other);

	EntityCriterion ltProperty(EntityProperty other);

	EntityCriterion gtProperty(EntityProperty other);

	EntityCriterion isNull();

	EntityCriterion isNotNull();

	EntityCriterion isEmpty();

	EntityCriterion isNotEmpty();

	void sort();

	void sort(boolean ascending);

	void sort(boolean ascending, boolean ignoreCase);

}
