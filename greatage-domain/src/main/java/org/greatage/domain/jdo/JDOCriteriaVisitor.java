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

package org.greatage.domain.jdo;

import org.greatage.domain.AbstractCriteriaVisitor;
import org.greatage.domain.Criteria;
import org.greatage.domain.Entity;
import org.greatage.domain.JunctionCriteria;
import org.greatage.domain.PropertyCriteria;
import org.greatage.domain.SortCriteria;

import javax.jdo.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JDOCriteriaVisitor<PK extends Serializable, E extends Entity<PK>>
		extends AbstractCriteriaVisitor<PK, E> {
	private final Map<String, Object> parameters = new HashMap<String, Object>();
	private final List<String> names = new ArrayList<String>();

	private final Query query;
	private List<String> junction = new ArrayList<String>();
	private int level;

	public JDOCriteriaVisitor(final Query query) {
		this.query = query;
	}

	@Override
	public void visit(final Criteria<PK, E> criteria) {
		level++;
		super.visit(criteria);
		level--;
		// google-app-engine fix for empty filter
		if (level == 0 && !junction.isEmpty()) {
			query.setFilter(getJunction(junction, " && "));
		}
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	@Override
	protected void visitJunction(final JunctionCriteria<PK, E> criteria) {
		final List<String> parent = this.junction;
		junction = new ArrayList<String>();

		for (Criteria<PK, E> child : criteria.getChildren()) {
			visit(child);
		}

		final List<String> temp = junction;
		junction = parent;

		final String function = criteria.getOperator() == JunctionCriteria.Operator.AND ? " && " : " || ";
		final String filter = getJunction(temp, function);
		addCriterion(filter, criteria.isNegative());
	}

	@Override
	protected void visitProperty(final PropertyCriteria<PK, E> criteria) {
		switch (criteria.getOperator()) {
			case EQUAL:
				visitEqualOperator(criteria);
				break;
			case NOT_EQUAL:
				visitNotEqualOperator(criteria);
				break;
			case GREATER_THAN:
				visitGreaterThanOperator(criteria);
				break;
			case GREATER_OR_EQUAL:
				visitGreaterOrEqualOperator(criteria);
				break;
			case LESS_THAN:
				visitLessThanOperator(criteria);
				break;
			case LESS_OR_EQUAL:
				visitLessOrEqualOperator(criteria);
				break;
			case LIKE:
				visitLikeOperation(criteria);
				break;
			case IN:
				visitInOperator(criteria);
				break;
		}
	}

	private void visitLikeOperation(final PropertyCriteria<PK, E> criteria) {
		//TODO:
	}

	@Override
	protected void visitSort(final SortCriteria<PK, E> criteria) {
		//TODO:
	}

	private void visitEqualOperator(final PropertyCriteria<PK, E> criteria) {
		final String propertyName = propertyName(criteria);
		final String parameterName = parameterName(criteria);
		final String criterion = propertyName + " == :" + parameterName;

		addParameter(parameterName, criteria.getValue());
		addCriterion(criterion, criteria.isNegative());
	}

	private void visitNotEqualOperator(final PropertyCriteria<PK, E> criteria) {
		final String propertyName = propertyName(criteria);

		if (criteria.getValue() != null) {
			final String parameterName = parameterName(criteria);

			// fix for null values
			final String criterion = "(" +
					propertyName + " == null || " +
					propertyName + " != :" + parameterName + ")";
			addParameter(parameterName, criteria.getValue());
			addCriterion(criterion, criteria.isNegative());
		} else {
			final String criterion = propertyName + " != null";
			addCriterion(criterion, criteria.isNegative());
		}
	}

	private void visitGreaterThanOperator(final PropertyCriteria<PK, E> criteria) {
		final String propertyName = propertyName(criteria);

		if (criteria.getValue() != null) {
			final String parameterName = parameterName(criteria);

			final String criterion = propertyName + " > :" + parameterName;
			addParameter(parameterName, criteria.getValue());
			addCriterion(criterion, criteria.isNegative());
		} else {
			// all not null values is greater than null
			final String criterion = propertyName + " != null";
			addCriterion(criterion, criteria.isNegative());
		}
	}

	private void visitGreaterOrEqualOperator(final PropertyCriteria<PK, E> criteria) {
		final String propertyName = propertyName(criteria);

		if (criteria.getValue() != null) {
			final String parameterName = parameterName(criteria);

			final String criterion = propertyName + " >= :" + parameterName;
			addParameter(parameterName, criteria.getValue());
			addCriterion(criterion, criteria.isNegative());
		} else {
			// all values is greater or equal null
			final String criterion = "true";
			addCriterion(criterion, criteria.isNegative());
		}
	}

	private void visitLessThanOperator(final PropertyCriteria<PK, E> criteria) {
		final String propertyName = propertyName(criteria);

		if (criteria.getValue() != null) {
			final String parameterName = parameterName(criteria);

			final String criterion = "(" + propertyName + " == null || " + propertyName + " < :" + parameterName + ")";
			addParameter(parameterName, criteria.getValue());
			addCriterion(criterion, criteria.isNegative());
		} else {
			// there are no values less than null
			final String criterion = "false";
			addCriterion(criterion, criteria.isNegative());
		}
	}

	private void visitLessOrEqualOperator(final PropertyCriteria<PK, E> criteria) {
		final String propertyName = propertyName(criteria);

		if (criteria.getValue() != null) {
			final String parameterName = parameterName(criteria);

			final String criterion = "(" + propertyName + " == null || " + propertyName + " <= :" + parameterName + ")";
			addParameter(parameterName, criteria.getValue());
			addCriterion(criterion, criteria.isNegative());
		} else {
			final String criterion = propertyName + " == null";
			addCriterion(criterion, criteria.isNegative());
		}
	}

	private void visitInOperator(final PropertyCriteria<PK, E> criteria) {
		final String propertyName = propertyName(criteria);
		final List<?> value = (List<?>) criteria.getValue();

		if (value == null || value.isEmpty()) {
			addCriterion("false", criteria.isNegative());
		} else if (value.contains(null)) {
			final String parameterName = parameterName(criteria);

			final String criterion = "(" + propertyName + " == null || :" + parameterName + ".contains(" + propertyName + "))";

			final List<Object> recalculated = new ArrayList<Object>();
			for (Object val : value) {
				if (val != null) {
					recalculated.add(val);
				}
			}
			addParameter(parameterName, recalculated);
			addCriterion(criterion, criteria.isNegative());
		} else {
			final String parameterName = parameterName(criteria);

			final String criterion = ":" + parameterName + ".contains(" + propertyName + ")";
			addParameter(parameterName, value);
			addCriterion(criterion, criteria.isNegative());
		}
	}

	private void addParameter(final String parameterName, final Object value) {
		parameters.put(parameterName, value);
	}

	private void addCriterion(final String criterion, final boolean negative) {
		junction.add(negative ? "!(" + criterion + ")" : criterion);
	}

	private String getJunction(final List<String> children, final String function) {
		if (children.isEmpty()) {
			return "";
		}
		if (children.size() == 1) {
			return children.get(0);
		}
		final StringBuilder criterion = new StringBuilder();
		for (String child : children) {
			if (criterion.length() > 0) {
				criterion.append(function);
			}
			criterion.append(child);
		}
		return criterion.toString();
	}

	private String propertyName(final PropertyCriteria<PK, E> criteria) {
		return criteria.getPath() != null ?
				criteria.getPath() + "." + criteria.getProperty() :
				criteria.getProperty();
	}

	private String parameterName(final PropertyCriteria<PK, E> criteria) {
		return allocateName(criteria.getProperty());
	}

	private String allocateName(final String name) {
		String result = name + "_";
		int i = 0;
		while (names.contains(result)) {
			result = name + "_" + i++;
		}
		names.add(result);
		return result;
	}
}
