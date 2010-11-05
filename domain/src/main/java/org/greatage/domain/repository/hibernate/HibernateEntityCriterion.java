package org.greatage.domain.repository.hibernate;

import org.greatage.domain.repository.EntityCriterion;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

/**
 * @author Ivan Khalopik
 */
public class HibernateEntityCriterion implements EntityCriterion {
	private final Criterion criterion;

	public HibernateEntityCriterion(Criterion criterion) {
		this.criterion = criterion;
	}

	Criterion getCriterion() {
		return criterion;
	}

	public EntityCriterion or(EntityCriterion... criterions) {
		final Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(getCriterion());
		for (EntityCriterion criterion : criterions) {
			disjunction.add(((HibernateEntityCriterion) criterion).getCriterion());
		}
		return new HibernateEntityCriterion(disjunction);
	}

	public EntityCriterion and(EntityCriterion... criterions) {
		final Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(getCriterion());
		for (EntityCriterion criterion : criterions) {
			conjunction.add(((HibernateEntityCriterion) criterion).getCriterion());
		}
		return new HibernateEntityCriterion(conjunction);
	}

	public EntityCriterion not() {
		return new HibernateEntityCriterion(Restrictions.not(getCriterion()));
	}
}
