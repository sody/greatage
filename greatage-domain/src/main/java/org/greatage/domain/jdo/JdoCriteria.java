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
import org.greatage.domain.sql.SqlCriterion;
import org.greatage.util.DescriptionBuilder;
import org.greatage.util.StringUtils;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JdoCriteria implements EntityCriteria {
	private final Query query;
	private final String alias;

	private final Map<String, EntityProperty> properties = new HashMap<String, EntityProperty>();
	private final Map<String, JdoCriteria> criterias = new HashMap<String, JdoCriteria>();

	private SqlCriterion filter;

	private JdoCriteria(final Query query, final String alias) {
		this.query = query;
		this.alias = alias;
	}

	public static JdoCriteria forClass(final PersistenceManager pm, final Class<? extends Entity> entityClass) {
		final Extent<? extends Entity> extent = pm.getExtent(entityClass, true);
		final Query query = pm.newQuery(extent);
		return new JdoCriteria(query, null);
	}

	Query assign() {
		if (filter != null) {
			query.setFilter(filter.getSql());
		}
		return query;
	}

	public String getAlias() {
		return alias;
	}

	public EntityCriteria root() {
		return this;
	}

	public JdoCriteria getCriteria(final String path) {
		if (!criterias.containsKey(path)) {
			criterias.put(path, createCriteria(path));
		}
		return criterias.get(path);
	}

	public EntityProperty getProperty(final String path) {
		if (!properties.containsKey(path)) {
			properties.put(path, createProperty(path));
		}
		return properties.get(path);
	}

	public void add(final EntityCriterion criterion) {
		if (filter == null) {
			filter = (SqlCriterion) criterion;
		} else {
			filter.and(criterion);
		}
	}

	public void setPagination(final Pagination pagination) {
		if (pagination.getCount() >= 0) {
			final int start = pagination.getStart() > 0 ? pagination.getStart() : 0;
			final int end = start + pagination.getCount();
			query.setRange(start, end);
		}
	}

	private JdoCriteria createCriteria(final String path) {
		if (StringUtils.isEmpty(path)) {
			throw new IllegalArgumentException("Empty path");
		}
		return new JdoCriteria(query, path);
	}

	private EntityProperty createProperty(final String path) {
		if (StringUtils.isEmpty(path)) {
			throw new IllegalArgumentException("Empty path");
		}
		final int i = path.lastIndexOf('.');
		final JdoCriteria entityCriteria = i > 0 ? getCriteria(path.substring(0, i)) : this;
		final String property = i > 0 ? path.substring(i + 1) : path;
		return new JdoProperty(entityCriteria.query, entityCriteria.getAlias(), property);
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(query);
		return builder.toString();
	}
}
