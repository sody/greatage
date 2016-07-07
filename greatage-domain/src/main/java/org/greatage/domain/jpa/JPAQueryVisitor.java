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
import org.greatage.domain.internal.*;

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
    protected void visitJunction(final JunctionCriteria criteria) {
        //todo: implement this
    }

    @Override
    protected void visitNegative(final NegativeCriteria criteria) {
        //TODO: implement it
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visitChild(final ChildCriteria criteria) {
        //todo: implement this
    }

    @Override
    protected void visitAll(final AllCriteria criteria) {
        //TODO: implement it
    }

    @Override
    protected void visitEqual(final PropertyCriteria criteria) {
        //todo: implement this
    }

    @Override
    protected void visitNotEqual(final PropertyCriteria criteria) {
        //todo: implement this
    }

    @Override
    protected void visitGreaterThan(final PropertyCriteria criteria) {
        //todo: implement this
    }

    @Override
    protected void visitGreaterOrEqual(final PropertyCriteria criteria) {
        //todo: implement this
    }

    @Override
    protected void visitLessThan(final PropertyCriteria criteria) {
        //todo: implement this
    }

    @Override
    protected void visitLessOrEqual(final PropertyCriteria criteria) {
        //todo: implement this
    }

    @Override
    protected void visitIn(final PropertyCriteria criteria) {
        //todo: implement this
    }

    @Override
    protected void visitNotIn(final PropertyCriteria criteria) {
        //todo: implement this
    }

    @Override
    protected void visitLike(final PropertyCriteria criteria) {
        //todo: implement this
    }

    @Override
    protected void visitFetch(final org.greatage.domain.Query.Property fetch) {
        //todo: implement this
    }

    @Override
    protected void visitSort(final org.greatage.domain.Query.Property property, final boolean ascending, final boolean ignoreCase) {
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
