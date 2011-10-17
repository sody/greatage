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

import org.greatage.domain.EntityCriterion;
import org.greatage.domain.EntityProperty;
import org.greatage.util.DescriptionBuilder;

import javax.jdo.Query;
import java.util.Collection;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JdoProperty implements EntityProperty {
	private final String property;
	private final String fullProperty;
	private final Query query;

	public JdoProperty(final Query query, final String path, final String property) {
		this.query = query;
		this.property = property;
		this.fullProperty = path != null ? path + "." + property : property;
	}

	public String getProperty() {
		return property;
	}

	protected String property() {
		return fullProperty;
	}

	public void sort() {
		sort(true, true);
	}

	public void sort(final boolean ascending) {
		sort(ascending, true);
	}

	public void sort(final boolean ascending, final boolean ignoreCase) {
		query.setOrdering(property());
	}

	public EntityCriterion in(final Collection values) {
		return in(values.toArray());
	}

	public EntityCriterion in(final Object[] values) {
		if (values.length == 0) {
			return null;
		} else if (values.length == 1) {
			return eq(values[0]);
		}
		final StringBuilder builder = new StringBuilder();
		builder.append('(');
		for (Object value : values) {
			if (builder.length() > 1) {
				builder.append(',');
			}
			builder.append(wrap(value));
		}
		builder.append(')');
		return raw("in", builder.toString());
	}

	public EntityCriterion like(Object value) {
		return jql("like", value);
	}

	public EntityCriterion eq(Object value) {
		return jql("==", value);
	}

	public EntityCriterion ne(Object value) {
		return jql("!=", value);
	}

	public EntityCriterion gt(Object value) {
		return jql(">", value);
	}

	public EntityCriterion lt(Object value) {
		return jql("<", value);
	}

	public EntityCriterion le(Object value) {
		return jql("<=", value);
	}

	public EntityCriterion ge(Object value) {
		return jql(">=", value);
	}

	public EntityCriterion between(final Object min, final Object max) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public EntityCriterion eqProperty(final EntityProperty other) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public EntityCriterion neProperty(final EntityProperty other) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public EntityCriterion leProperty(final EntityProperty other) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public EntityCriterion geProperty(final EntityProperty other) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public EntityCriterion ltProperty(final EntityProperty other) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public EntityCriterion gtProperty(final EntityProperty other) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public EntityCriterion isNull() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public EntityCriterion isNotNull() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public EntityCriterion isEmpty() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public EntityCriterion isNotEmpty() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	private JdoCriterion jql(final String operation, final Object value) {
		return raw(operation, wrap(value));
	}

	private JdoCriterion raw(final String operation, final String value) {
		return new JdoCriterion(property(), operation, value);
	}

	private String wrap(final Object value) {
		if (value instanceof Number || value instanceof Boolean) {
			return String.valueOf(value);
		}
		return "'" + String.valueOf(value) + "'";
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(property);
		return builder.toString();
	}
}
