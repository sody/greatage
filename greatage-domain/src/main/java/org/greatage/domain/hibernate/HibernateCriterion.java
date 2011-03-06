/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
