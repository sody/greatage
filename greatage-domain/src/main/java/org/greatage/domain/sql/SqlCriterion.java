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

package org.greatage.domain.sql;

import org.greatage.domain.EntityCriterion;
import org.greatage.util.DescriptionBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SqlCriterion implements EntityCriterion {
	private final String sql;

	public SqlCriterion(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}

	public EntityCriterion or(final EntityCriterion... criterions) {
		final StringBuilder sb = new StringBuilder(getSql());
		for (EntityCriterion criterion : criterions) {
			sb.append(" or ");
			sb.append(((SqlCriterion) criterion).getSql());
		}
		return new SqlCriterion(sb.toString());
	}

	public EntityCriterion and(final EntityCriterion... criterions) {
		final StringBuilder sb = new StringBuilder(getSql());
		for (EntityCriterion criterion : criterions) {
			sb.append(" and ");
			sb.append(((SqlCriterion) criterion).getSql());
		}
		return new SqlCriterion(sb.toString());
	}

	public EntityCriterion not() {
		return new SqlCriterion("not " + getSql());
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(sql);
		return builder.toString();
	}
}
