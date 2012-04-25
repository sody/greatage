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

package org.greatage.domain;

import java.io.Serializable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractCriteriaVisitor<PK extends Serializable, E extends Entity<PK>> implements CriteriaVisitor<PK, E> {

	public void visit(final Criteria<PK, E> criteria) {
		if (criteria instanceof JunctionCriteria) {
			visitJunction((JunctionCriteria<PK, E>) criteria);
		} else if (criteria instanceof PropertyCriteria) {
			visitProperty((PropertyCriteria<PK, E>) criteria);
		} else if (criteria instanceof SortCriteria) {
			visitSort((SortCriteria<PK, E>) criteria);
		}
	}

	protected abstract void visitJunction(JunctionCriteria<PK, E> criteria);

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
				visitLikeOperator(criteria);
				break;
			case IN:
				visitInOperator(criteria);
				break;
		}
	}

	protected abstract void visitEqualOperator(PropertyCriteria<PK, E> criteria);

	protected abstract void visitNotEqualOperator(PropertyCriteria<PK, E> criteria);

	protected abstract void visitGreaterThanOperator(PropertyCriteria<PK, E> criteria);

	protected abstract void visitGreaterOrEqualOperator(PropertyCriteria<PK, E> criteria);

	protected abstract void visitLessThanOperator(PropertyCriteria<PK, E> criteria);

	protected abstract void visitLessOrEqualOperator(PropertyCriteria<PK, E> criteria);

	protected abstract void visitInOperator(PropertyCriteria<PK, E> criteria);

	protected abstract void visitLikeOperator(PropertyCriteria<PK, E> criteria);

	protected abstract void visitSort(SortCriteria<PK, E> criteria);
}
