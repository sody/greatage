/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository.hibernate;

import org.greatage.domain.Entity;
import org.greatage.domain.Pagination;
import org.greatage.domain.repository.EntityCriteria;
import org.greatage.domain.repository.EntityCriterion;
import org.greatage.domain.repository.EntityProperty;
import org.greatage.util.DescriptionBuilder;
import org.greatage.util.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.impl.CriteriaImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HibernateCriteria implements EntityCriteria {
	private final Criteria criteria;
	private final EntityCriteria root;

	private final Map<String, EntityProperty> properties = new HashMap<String, EntityProperty>();
	private final Map<String, HibernateCriteria> criterias = new HashMap<String, HibernateCriteria>();

	private final List<String> names = new ArrayList<String>();

	private HibernateCriteria(final Criteria criteria, final EntityCriteria root) {
		this.criteria = criteria;
		this.root = root;
	}

	public static HibernateCriteria forClass(final Class<? extends Entity> entityClass) {
		final CriteriaImpl criteria = new CriteriaImpl(entityClass.getName(), null);
		return new HibernateCriteria(criteria, null);
	}

	public String getAlias() {
		return criteria.getAlias();
	}

	public EntityCriteria root() {
		return root != null ? root : this;
	}

	public HibernateCriteria getCriteria(final String path) {
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
		criteria.add(((HibernateCriterion) criterion).getCriterion());
	}

	public void setPagination(final Pagination pagination) {
		if (pagination.getStart() > 0) {
			criteria.setFirstResult(pagination.getStart());
		}
		if (pagination.getCount() >= 0) {
			criteria.setMaxResults(pagination.getCount());
		}
	}

	Criteria assign(final Session session) {
		CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;
		criteriaImpl.setSession((SessionImplementor) session);
		return criteriaImpl;
	}

	private HibernateCriteria createCriteria(final String path) {
		if (!StringUtils.isEmpty(path)) {
			throw new IllegalArgumentException("Empty path");
		}
		final int i = path.lastIndexOf('.');
		final String property = i > 0 ? path.substring(i + 1) : path;
		final Criteria subCriteria = criteria.createCriteria(path, allocateName(property));
		return new HibernateCriteria(subCriteria, root());
	}

	private EntityProperty createProperty(final String path) {
		if (!StringUtils.isEmpty(path)) {
			throw new IllegalArgumentException("Empty path");
		}
		final int i = path.lastIndexOf('.');
		final HibernateCriteria entityCriteria = i > 0 ? getCriteria(path.substring(0, i)) : this;
		final String property = i > 0 ? path.substring(i + 1) : path;
		return new HibernateProperty(entityCriteria.criteria, property);
	}

	private String allocateName(final String name) {
		String result = name;
		int i = 0;
		while (names.contains(result)) {
			result = name + "_" + i++;
		}
		names.add(result);
		return result;
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(criteria);
		return builder.toString();
	}
}
