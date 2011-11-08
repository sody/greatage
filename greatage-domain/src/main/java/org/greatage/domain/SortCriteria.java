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
public class SortCriteria<PK extends Serializable, E extends Entity<PK>> extends AllCriteria<PK, E> {
	private final String path;
	private final String property;
	private final boolean ascending;
	private final boolean ignoreCase;

	public SortCriteria(final String path, final String property, final boolean ascending, final boolean ignoreCase) {
		this.path = path;
		this.property = property;
		this.ascending = ascending;
		this.ignoreCase = ignoreCase;
	}

	@Override
	public Criteria<PK, E> not() {
		throw new UnsupportedOperationException("Can't negate sorting");
	}

	public String getPath() {
		return path;
	}

	public String getProperty() {
		return property;
	}

	public boolean isAscending() {
		return ascending;
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder("order by ");
		if (ignoreCase) {
			builder.append("lower(");
		}
		if (path != null) {
			builder.append(path).append('.');
		}
		builder.append(property);
		if (ignoreCase) {
			builder.append(")");
		}
		builder.append(ascending ? " asc" : " desc");
		return builder.toString();
	}
}
