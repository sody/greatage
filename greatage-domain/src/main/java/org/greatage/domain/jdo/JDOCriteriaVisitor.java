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

import org.greatage.domain.*;

import javax.jdo.Query;
import java.io.Serializable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JDOCriteriaVisitor<PK extends Serializable, E extends Entity<PK>>
		extends AbstractCriteriaVisitor<PK, E> {
	private final Query query;

	public JDOCriteriaVisitor(final Query query) {
		this.query = query;
	}

	@Override
	protected void visitGroup(final GroupCriteria<PK, E> criteria) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	protected void visitProperty(final PropertyCriteria<PK, E> pkePropertyCriteria) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	protected void visitSort(final SortCriteria<PK, E> pkeSortCriteria) {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
