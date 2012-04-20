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

import org.greatage.domain.AbstractCriteriaVisitor;
import org.greatage.domain.Criteria;
import org.greatage.domain.Entity;
import org.greatage.domain.JunctionCriteria;
import org.greatage.domain.PropertyCriteria;
import org.greatage.domain.SortCriteria;
import org.greatage.util.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HibernateCriteriaVisitor<PK extends Serializable, E extends Entity<PK>>
		extends AbstractCriteriaVisitor<PK, E> {

	private final Map<String, org.hibernate.Criteria> children = new HashMap<String, org.hibernate.Criteria>();
	private final List<String> names = new ArrayList<String>();

	private final org.hibernate.Criteria root;

	private Junction junction;

	public HibernateCriteriaVisitor(final org.hibernate.Criteria root) {
		this.root = root;
	}

	protected void visitJunction(final JunctionCriteria<PK, E> criteria) {
		final Junction parent = this.junction;
		junction = criteria.getOperator() == JunctionCriteria.Operator.AND ?
				Restrictions.conjunction() :
				Restrictions.disjunction();

		for (Criteria<PK, E> child : criteria.getChildren()) {
			visit(child);
		}

		final Junction temp = junction;
		junction = parent;

		addCriterion(temp, criteria.isNegative());
	}

	protected void visitProperty(final PropertyCriteria<PK, E> criteria) {
		final Property property = getProperty(criteria.getPath(), criteria.getProperty());
		switch (criteria.getOperator()) {
			case EQUAL:
				if (criteria.getValue() == null) {
					addCriterion(property.isNull(), criteria.isNegative());
				} else {
					addCriterion(property.eq(criteria.getValue()), criteria.isNegative());
				}
				break;
			case NOT_EQUAL:
				if (criteria.getValue() == null) {
					addCriterion(property.isNotNull(), criteria.isNegative());
				} else {
					addCriterion(Restrictions.or(
							property.isNull(),
							property.ne(criteria.getValue())
					), criteria.isNegative());
				}
				break;
			case GREATER_THAN:
				if (criteria.getValue() == null) {
					addCriterion(property.isNotNull(), criteria.isNegative());
				} else {
					addCriterion(property.gt(criteria.getValue()), criteria.isNegative());
				}
				break;
			case GREATER_OR_EQUAL:
				if (criteria.getValue() == null) {
					addCriterion(Restrictions.sqlRestriction("1=1"), criteria.isNegative());
				} else {
					addCriterion(property.ge(criteria.getValue()), criteria.isNegative());
				}
				break;
			case LESS_THAN:
				if (criteria.getValue() == null) {
					addCriterion(Restrictions.sqlRestriction("1=2"), criteria.isNegative());
				} else {
					addCriterion(Restrictions.or(
							property.isNull(),
							property.lt(criteria.getValue())
					), criteria.isNegative());
				}
				break;
			case LESS_OR_EQUAL:
				if (criteria.getValue() == null) {
					addCriterion(property.isNull(), criteria.isNegative());
				} else {
					addCriterion(Restrictions.or(
							property.isNull(),
							property.le(criteria.getValue())
					), criteria.isNegative());
				}
				break;
			case LIKE:
				addCriterion(property.like(criteria.getValue()), criteria.isNegative());
				break;
			case IN:
				final List<?> value = (List<?>) criteria.getValue();
				if (value == null || value.isEmpty()) {
					addCriterion(Restrictions.sqlRestriction("1=2"), criteria.isNegative());
				} else if (value.contains(null)) {
					final List<Object> recalculated = new ArrayList<Object>();
					for (Object val : value) {
						if (val != null) {
							recalculated.add(val);
						}
					}
					if (recalculated.size() > 0) {
						addCriterion(Restrictions.or(
								property.isNull(),
								property.in(recalculated)
						), criteria.isNegative());
					} else {
						addCriterion(property.isNull(), criteria.isNegative());
					}
				} else {
					addCriterion(property.in(value), criteria.isNegative());
				}
				break;
		}
	}

	protected void visitSort(final SortCriteria<PK, E> criteria) {
		final Order order = criteria.isAscending() ?
				Order.asc(criteria.getProperty()) :
				Order.desc(criteria.getProperty());

		if (criteria.isIgnoreCase()) {
			order.ignoreCase();
		}
		getCriteria(criteria.getPath()).addOrder(order);
	}

	private void addCriterion(final Criterion criterion, final boolean negative) {
		if (junction != null) {
			junction.add(negative ? Restrictions.not(criterion) : criterion);
		} else {
			root.add(negative ? Restrictions.not(criterion) : criterion);
		}
	}

	private Property getProperty(final String path, final String property) {
		final String alias = getCriteria(path).getAlias();
		return Property.forName(alias + "." + property);
	}

	private org.hibernate.Criteria getCriteria(final String path) {
		if (path == null) {
			return root;
		}
		if (!children.containsKey(path)) {
			children.put(path, createCriteria(path));
		}
		return children.get(path);
	}

	private org.hibernate.Criteria createCriteria(final String path) {
		if (StringUtils.isEmpty(path)) {
			throw new IllegalArgumentException("Empty path");
		}
		final int i = path.lastIndexOf('.');
		final String property = i > 0 ? path.substring(i + 1) : path;
		return root.createCriteria(path, allocateName(property));
	}

	private String allocateName(final String name) {
		String result = name;
		int i = 0;
		while (names.contains(result)) {
			result = name + "_" + i++;
		}
		names.add(result);
		return result;
	}
}
