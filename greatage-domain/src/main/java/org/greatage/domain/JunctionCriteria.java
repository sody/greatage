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
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JunctionCriteria<PK extends Serializable, E extends Entity<PK>> extends AllCriteria<PK, E> {
	private final List<Criteria<PK, E>> children;
	private final Operator operator;

	public JunctionCriteria(final Operator operator, final List<Criteria<PK, E>> children) {
		this.operator = operator;
		this.children = children;
	}

	@Override
	public Criteria<PK, E> and(final Criteria<PK, E> criteria) {
		if (operator == Operator.AND) {
			children.add(criteria);
			return this;
		}
		return super.and(criteria);
	}

	@Override
	public Criteria<PK, E> or(final Criteria<PK, E> criteria) {
		if (operator == Operator.OR) {
			children.add(criteria);
			return this;
		}
		return super.or(criteria);
	}

	public List<Criteria<PK, E>> getChildren() {
		return children;
	}

	public Operator getOperator() {
		return operator;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder("(");
		for (Criteria<PK, E> child : children) {
			if (builder.length() > 1) {
				builder.append(operator == Operator.AND ? " and " : " or ");
			}
			builder.append(child);
		}
		builder.append(")");
		return builder.toString();
	}

	public enum Operator {
		AND,
		OR
	}
}
