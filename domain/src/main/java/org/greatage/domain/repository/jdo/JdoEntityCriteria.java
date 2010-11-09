/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository.jdo;

import org.greatage.domain.Entity;
import org.greatage.domain.Pagination;
import org.greatage.domain.repository.EntityCriteria;
import org.greatage.domain.repository.EntityCriterion;
import org.greatage.domain.repository.EntityProperty;
import org.greatage.domain.repository.sql.SqlCriterion;
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
public class JdoEntityCriteria implements EntityCriteria {
	private final Query query;
	private final String alias;

	private final Map<String, EntityProperty> properties = new HashMap<String, EntityProperty>();
	private final Map<String, JdoEntityCriteria> criterias = new HashMap<String, JdoEntityCriteria>();

	private SqlCriterion filter;

	private JdoEntityCriteria(final Query query, final String alias) {
		this.query = query;
		this.alias = alias;
	}

	public static JdoEntityCriteria forClass(final PersistenceManager pm, final Class<? extends Entity> entityClass) {
		final Extent<? extends Entity> extent = pm.getExtent(entityClass, true);
		final Query query = pm.newQuery(extent);
		return new JdoEntityCriteria(query, null);
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

	public JdoEntityCriteria getCriteria(final String path) {
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

	private JdoEntityCriteria createCriteria(final String path) {
		if (!StringUtils.isEmpty(path)) {
			throw new IllegalArgumentException("Empty path");
		}
		return new JdoEntityCriteria(query, path);
	}

	private EntityProperty createProperty(final String path) {
		if (!StringUtils.isEmpty(path)) {
			throw new IllegalArgumentException("Empty path");
		}
		final int i = path.lastIndexOf('.');
		final JdoEntityCriteria entityCriteria = i > 0 ? getCriteria(path.substring(0, i)) : this;
		final String property = i > 0 ? path.substring(i + 1) : path;
		return new JdoEntityProperty(entityCriteria.query, entityCriteria.getAlias(), property);
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(query);
		return builder.toString();
	}
}
