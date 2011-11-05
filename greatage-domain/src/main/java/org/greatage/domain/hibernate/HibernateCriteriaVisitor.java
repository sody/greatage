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

import org.greatage.domain.*;
import org.greatage.util.StringUtils;
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
public class HibernateCriteriaVisitor<PK extends Serializable, E extends Entity<PK>> implements CriteriaVisitor<PK, E> {
	private final Map<String, org.hibernate.Criteria> criterias = new HashMap<String, org.hibernate.Criteria>();
	private final List<String> names = new ArrayList<String>();

	private final org.hibernate.Criteria root;

	private Junction junction;

	public HibernateCriteriaVisitor(final org.hibernate.Criteria root) {
		junction = Restrictions.conjunction();
		root.add(junction);
		this.root = root;
	}

	public void visit(final Criteria<PK, E> criteria) {
		if (criteria instanceof GroupCriteria) {
			visitGroup((GroupCriteria<PK, E>) criteria);
		} else if (criteria instanceof PropertyCriteria) {
			visitProperty((PropertyCriteria<PK, E>) criteria);
		} else if (criteria instanceof SortCriteria) {
			visitSort((SortCriteria<PK, E>) criteria);
		}
	}

	private void visitGroup(final GroupCriteria<PK, E> criteria) {
		final Junction parent = this.junction;
		junction = criteria.getOperator() == GroupCriteria.Operator.AND ?
				Restrictions.conjunction() :
				Restrictions.conjunction();

		for (Criteria<PK, E> child : criteria.getChildren()) {
			visit(child);
		}

		parent.add(junction);
		junction = parent;
	}

	private void visitProperty(final PropertyCriteria<PK, E> criteria) {
		final Property property = property(criteria.getPath(), criteria.getProperty());
		switch (criteria.getOperator()) {
			case GREATER_THAN:
				junction.add(property.gt(criteria.getValue()));
				break;
			case GREATER_OR_EQUAL:
				junction.add(property.ge(criteria.getValue()));
				break;
			case EQUAL:
				junction.add(property.eq(criteria.getValue()));
				break;
		}
	}

	private void visitSort(final SortCriteria<PK, E> criteria) {
		final Order order = criteria.isAscending() ?
				Order.asc(criteria.getProperty()) :
				Order.desc(criteria.getProperty());

		if (criteria.isIgnoreCase()) {
			order.ignoreCase();
		}
		criteria(criteria.getPath()).addOrder(order);
	}

	private Property property(final String path, final String property) {
		final String alias = criteria(path).getAlias();
		return Property.forName(alias + "." + property);
	}

	private org.hibernate.Criteria criteria(final String path) {
		if (path == null) {
			return root;
		}
		if (!criterias.containsKey(path)) {
			criterias.put(path, createCriteria(path));
		}
		return criterias.get(path);
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
