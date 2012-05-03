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

package org.greatage.domain.jpa;

import org.greatage.domain.Entity;
import org.greatage.domain.Repository;
import org.greatage.domain.internal.AbstractQueryVisitor;
import org.greatage.domain.internal.JunctionCriteria;
import org.greatage.domain.internal.PropertyCriteria;

import javax.persistence.Query;
import java.io.Serializable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JPAQueryVisitor<PK extends Serializable, E extends Entity<PK>>
		extends AbstractQueryVisitor<PK, E> {
	private final Query query;

	public JPAQueryVisitor(final Query query) {
		this.query = query;
	}

	@Override
	protected void visitJunction(final JunctionCriteria<PK, E> criteria) {
		//todo: implement this
	}

	@Override
	protected void visitEqual(final PropertyCriteria<PK, E> criteria) {
		//todo: implement this
	}

	@Override
	protected void visitNotEqual(final PropertyCriteria<PK, E> criteria) {
		//todo: implement this
	}

	@Override
	protected void visitGreaterThan(final PropertyCriteria<PK, E> criteria) {
		//todo: implement this
	}

	@Override
	protected void visitGreaterOrEqual(final PropertyCriteria<PK, E> criteria) {
		//todo: implement this
	}

	@Override
	protected void visitLessThan(final PropertyCriteria<PK, E> criteria) {
		//todo: implement this
	}

	@Override
	protected void visitLessOrEqual(final PropertyCriteria<PK, E> criteria) {
		//todo: implement this
	}

	@Override
	protected void visitIn(final PropertyCriteria<PK, E> criteria) {
		//todo: implement this
	}

	@Override
	protected void visitLike(final PropertyCriteria<PK, E> criteria) {
		//todo: implement this
	}

	@Override
	protected void visitFetch(final Repository.Property fetch) {
		//todo: implement this
	}

	@Override
	protected void visitProjection(final Repository.Property property, final String key) {
		//todo: implement this
	}

	@Override
	protected void visitSort(final Repository.Property property, final boolean ascending, final boolean ignoreCase) {
		//todo: implement this
	}

	@Override
	protected void visitPagination(final int start, final int count) {
		if (start > 0) {
			query.setFirstResult(start);
		}
		if (count >= 0) {
			query.setMaxResults(count);
		}
	}
}