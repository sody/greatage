/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository.hibernate;

import org.greatage.domain.repository.EntityCriterion;
import org.greatage.domain.repository.EntityProperty;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import java.util.Collection;

/**
 * @author Ivan Khalopik
 */
public class HibernateEntityProperty implements EntityProperty {
	private final Criteria criteria;
	private final String property;

	public HibernateEntityProperty(Criteria criteria, String property) {
		this.criteria = criteria;
		this.property = property;
	}

	public String getProperty() {
		return property;
	}

	private Property property() {
		return Property.forName(criteria.getAlias() + "." + getProperty());
	}

	private HibernateEntityCriterion criterion(Criterion criterion) {
		return new HibernateEntityCriterion(criterion);
	}

	public EntityCriterion alwaysTrue() {
		return criterion(Restrictions.sqlRestriction("1=1"));
	}

	public EntityCriterion alwaysFalse() {
		return criterion(Restrictions.sqlRestriction("1=2"));
	}

	public EntityCriterion in(Collection values) {
		return values.isEmpty() ? alwaysFalse() : criterion(property().in(values));
	}

	public EntityCriterion in(Object[] values) {
		return values.length == 0 ? alwaysFalse() : criterion(property().in(values));
	}

	public EntityCriterion like(Object value) {
		return criterion(property().like(value));
	}

	public EntityCriterion eq(Object value) {
		return criterion(property().eq(value));
	}

	public EntityCriterion ne(Object value) {
		return criterion(property().ne(value));
	}

	public EntityCriterion gt(Object value) {
		return criterion(property().gt(value));
	}

	public EntityCriterion lt(Object value) {
		return criterion(property().lt(value));
	}

	public EntityCriterion le(Object value) {
		return criterion(property().le(value));
	}

	public EntityCriterion ge(Object value) {
		return criterion(property().ge(value));
	}

	public EntityCriterion between(Object min, Object max) {
		return criterion(property().between(min, max));
	}

	public EntityCriterion eqProperty(EntityProperty other) {
		final Property otherProperty = ((HibernateEntityProperty) other).property();
		return criterion(property().eqProperty(otherProperty));
	}

	public EntityCriterion neProperty(EntityProperty other) {
		final Property otherProperty = ((HibernateEntityProperty) other).property();
		return criterion(property().neProperty(otherProperty));
	}

	public EntityCriterion leProperty(EntityProperty other) {
		final Property otherProperty = ((HibernateEntityProperty) other).property();
		return criterion(property().leProperty(otherProperty));
	}

	public EntityCriterion geProperty(EntityProperty other) {
		final Property otherProperty = ((HibernateEntityProperty) other).property();
		return criterion(property().geProperty(otherProperty));
	}

	public EntityCriterion ltProperty(EntityProperty other) {
		final Property otherProperty = ((HibernateEntityProperty) other).property();
		return criterion(property().ltProperty(otherProperty));
	}

	public EntityCriterion gtProperty(EntityProperty other) {
		final Property otherProperty = ((HibernateEntityProperty) other).property();
		return criterion(property().gtProperty(otherProperty));
	}

	public EntityCriterion isNull() {
		return criterion(property().isNull());
	}

	public EntityCriterion isNotNull() {
		return criterion(property().isNotNull());
	}

	public EntityCriterion isEmpty() {
		return criterion(property().isEmpty());
	}

	public EntityCriterion isNotEmpty() {
		return criterion(property().isNotEmpty());
	}

	public void sort() {
		sort(true);
	}

	public void sort(boolean ascending) {
		sort(ascending, true);
	}

	public void sort(boolean ascending, boolean ignoreCase) {
		final Order order = ascending ? property().asc() : property().desc();
		if (ignoreCase) {
			order.ignoreCase();
		}
		criteria.addOrder(order);
	}
}
