/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.hibernate;

import org.greatage.domain.EntityCriterion;
import org.greatage.domain.EntityProperty;
import org.greatage.util.DescriptionBuilder;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import java.util.Collection;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HibernateProperty implements EntityProperty {
	private final Criteria criteria;
	private final String property;

	public HibernateProperty(final Criteria criteria, final String property) {
		this.criteria = criteria;
		this.property = property;
	}

	public String getProperty() {
		return property;
	}

	private Property property() {
		return Property.forName(criteria.getAlias() + "." + getProperty());
	}

	private HibernateCriterion criterion(final Criterion criterion) {
		return new HibernateCriterion(criterion);
	}

	public EntityCriterion alwaysTrue() {
		return criterion(Restrictions.sqlRestriction("1=1"));
	}

	public EntityCriterion alwaysFalse() {
		return criterion(Restrictions.sqlRestriction("1=2"));
	}

	public EntityCriterion in(final Collection values) {
		return values.isEmpty() ? alwaysFalse() : criterion(property().in(values));
	}

	public EntityCriterion in(final Object[] values) {
		return values.length == 0 ? alwaysFalse() : criterion(property().in(values));
	}

	public EntityCriterion like(final Object value) {
		return criterion(property().like(value));
	}

	public EntityCriterion eq(final Object value) {
		return criterion(property().eq(value));
	}

	public EntityCriterion ne(final Object value) {
		return criterion(property().ne(value));
	}

	public EntityCriterion gt(final Object value) {
		return criterion(property().gt(value));
	}

	public EntityCriterion lt(final Object value) {
		return criterion(property().lt(value));
	}

	public EntityCriterion le(final Object value) {
		return criterion(property().le(value));
	}

	public EntityCriterion ge(final Object value) {
		return criterion(property().ge(value));
	}

	public EntityCriterion between(final Object min, final Object max) {
		return criterion(property().between(min, max));
	}

	public EntityCriterion eqProperty(final EntityProperty other) {
		final Property otherProperty = ((HibernateProperty) other).property();
		return criterion(property().eqProperty(otherProperty));
	}

	public EntityCriterion neProperty(final EntityProperty other) {
		final Property otherProperty = ((HibernateProperty) other).property();
		return criterion(property().neProperty(otherProperty));
	}

	public EntityCriterion leProperty(final EntityProperty other) {
		final Property otherProperty = ((HibernateProperty) other).property();
		return criterion(property().leProperty(otherProperty));
	}

	public EntityCriterion geProperty(final EntityProperty other) {
		final Property otherProperty = ((HibernateProperty) other).property();
		return criterion(property().geProperty(otherProperty));
	}

	public EntityCriterion ltProperty(final EntityProperty other) {
		final Property otherProperty = ((HibernateProperty) other).property();
		return criterion(property().ltProperty(otherProperty));
	}

	public EntityCriterion gtProperty(final EntityProperty other) {
		final Property otherProperty = ((HibernateProperty) other).property();
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

	public void sort(final boolean ascending) {
		sort(ascending, true);
	}

	public void sort(final boolean ascending, final boolean ignoreCase) {
		final Order order = ascending ? property().asc() : property().desc();
		if (ignoreCase) {
			order.ignoreCase();
		}
		criteria.addOrder(order);
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(property);
		return builder.toString();
	}
}
