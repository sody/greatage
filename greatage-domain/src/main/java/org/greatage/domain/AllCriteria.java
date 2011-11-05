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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AllCriteria<PK extends Serializable, E extends Entity<PK>> implements Criteria<PK, E> {
	public Criteria<PK, E> and(final Criteria<PK, E> criteria, final Criteria<PK, E>... other) {
		return new GroupCriteria<PK, E>(GroupCriteria.Operator.AND, list(criteria, other));
	}

	public Criteria<PK, E> or(final Criteria<PK, E> criteria, final Criteria<PK, E>... other) {
		return new GroupCriteria<PK, E>(GroupCriteria.Operator.OR, list(criteria, other));
	}

	private List<Criteria<PK, E>> list(final Criteria<PK, E> criteria, final Criteria<PK, E>... other) {
		final List<Criteria<PK, E>> all = new ArrayList<Criteria<PK, E>>(other.length + 2);
		all.add(this);
		all.add(criteria);
		all.addAll(Arrays.asList(other));
		return all;
	}
}
