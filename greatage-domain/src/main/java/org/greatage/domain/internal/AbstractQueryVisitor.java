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

package org.greatage.domain.internal;

import org.greatage.domain.Entity;
import org.greatage.domain.Repository;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractQueryVisitor<PK extends Serializable, E extends Entity<PK>> {

	public void visitQuery(final AbstractQuery<PK, E> query) {
		if (query.getCriteria() != null) {
			visitCriteria(query.getCriteria());
		}
		if (query.getFetches() != null) {
			for (Repository.Property fetch : query.getFetches()) {
				visitFetch(fetch);
			}
		}
		if (query.getProjections() != null) {
			for (Map.Entry<String, Repository.Property> entry : query.getProjections().entrySet()) {
				visitProjection(entry.getValue(), entry.getKey());
			}
		}
		if (query.getSorts() != null) {
			for (AbstractQuery<PK, E>.Sort sort : query.getSorts()) {
				visitSort(sort.getProperty(), sort.isAscending(), sort.isIgnoreCase());
			}
		}
		visitPagination(query.getStart(), query.getCount());
	}

	protected void visitCriteria(final Repository.Criteria<PK, E> criteria) {
		if (criteria instanceof JunctionCriteria) {
			visitJunction((JunctionCriteria<PK, E>) criteria);
		} else if (criteria instanceof PropertyCriteria) {
			visitProperty((PropertyCriteria<PK, E>) criteria);
		}
	}

	protected void visitProperty(final PropertyCriteria<PK, E> criteria) {
		switch (criteria.getOperator()) {
			case EQUAL:
				visitEqual(criteria);
				break;
			case NOT_EQUAL:
				visitNotEqual(criteria);
				break;
			case GREATER_THAN:
				visitGreaterThan(criteria);
				break;
			case GREATER_OR_EQUAL:
				visitGreaterOrEqual(criteria);
				break;
			case LESS_THAN:
				visitLessThan(criteria);
				break;
			case LESS_OR_EQUAL:
				visitLessOrEqual(criteria);
				break;
			case LIKE:
				visitLike(criteria);
				break;
			case IN:
				visitIn(criteria);
				break;
		}
	}

	protected abstract void visitJunction(JunctionCriteria<PK, E> criteria);

	protected abstract void visitEqual(PropertyCriteria<PK, E> criteria);

	protected abstract void visitNotEqual(PropertyCriteria<PK, E> criteria);

	protected abstract void visitGreaterThan(PropertyCriteria<PK, E> criteria);

	protected abstract void visitGreaterOrEqual(PropertyCriteria<PK, E> criteria);

	protected abstract void visitLessThan(PropertyCriteria<PK, E> criteria);

	protected abstract void visitLessOrEqual(PropertyCriteria<PK, E> criteria);

	protected abstract void visitIn(PropertyCriteria<PK, E> criteria);

	protected abstract void visitLike(PropertyCriteria<PK, E> criteria);

	protected abstract void visitFetch(Repository.Property fetch);

	protected abstract void visitProjection(Repository.Property property, String key);

	protected abstract void visitSort(Repository.Property property, boolean ascending, boolean ignoreCase);

	protected abstract void visitPagination(int start, int count);
}
