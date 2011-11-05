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

package org.greatage.domain.cut;

import org.greatage.util.DescriptionBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JdoCriterion implements EntityCriterion {
	private final String jql;

	public JdoCriterion(final String property, final String operation, final String value) {
		this(new StringBuilder(property).append(' ').append(operation).append(' ').append(value).toString());
	}

	public JdoCriterion(final String jql) {
		this.jql = jql;
	}

	public String getJql() {
		return jql;
	}

	public JdoCriterion or(final EntityCriterion... criterions) {
		final StringBuilder sb = new StringBuilder(jql);
		for (EntityCriterion criterion : criterions) {
			sb.append(" || ");
			sb.append(((JdoCriterion) criterion).getJql());
		}
		return new JdoCriterion(sb.toString());
	}

	public EntityCriterion and(final EntityCriterion... criterions) {
		final StringBuilder sb = new StringBuilder(jql);
		for (EntityCriterion criterion : criterions) {
			sb.append(" && ");
			sb.append(((JdoCriterion) criterion).getJql());
		}
		return new JdoCriterion(sb.toString());
	}

	public EntityCriterion not() {
		return new JdoCriterion("! " + jql);
	}

	@Override
	public String toString() {
		return new DescriptionBuilder(getClass()).append(jql).toString();
	}
}
