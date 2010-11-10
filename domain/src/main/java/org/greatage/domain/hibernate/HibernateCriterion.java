/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.hibernate;

import org.greatage.domain.EntityCriterion;
import org.greatage.util.DescriptionBuilder;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HibernateCriterion implements EntityCriterion {
	private final Criterion criterion;

	public HibernateCriterion(final Criterion criterion) {
		this.criterion = criterion;
	}

	Criterion getCriterion() {
		return criterion;
	}

	public EntityCriterion or(final EntityCriterion... criterions) {
		final Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(getCriterion());
		for (EntityCriterion criterion : criterions) {
			disjunction.add(((HibernateCriterion) criterion).getCriterion());
		}
		return new HibernateCriterion(disjunction);
	}

	public EntityCriterion and(final EntityCriterion... criterions) {
		final Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(getCriterion());
		for (EntityCriterion criterion : criterions) {
			conjunction.add(((HibernateCriterion) criterion).getCriterion());
		}
		return new HibernateCriterion(conjunction);
	}

	public EntityCriterion not() {
		return new HibernateCriterion(Restrictions.not(getCriterion()));
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(criterion);
		return builder.toString();
	}
}
