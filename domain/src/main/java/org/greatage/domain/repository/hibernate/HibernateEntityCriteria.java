/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository.hibernate;

import org.greatage.domain.Entity;
import org.greatage.domain.Pagination;
import org.greatage.domain.repository.EntityCriteria;
import org.greatage.domain.repository.EntityCriterion;
import org.greatage.domain.repository.EntityProperty;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 */
public class HibernateEntityCriteria implements EntityCriteria {
	private final Criteria criteria;
	private final EntityCriteria root;

	private final Map<String, EntityProperty> properties = new HashMap<String, EntityProperty>();
	private final Map<String, HibernateEntityCriteria> criterias = new HashMap<String, HibernateEntityCriteria>();

	private final List<String> names = new ArrayList<String>();

	private HibernateEntityCriteria(Criteria criteria, EntityCriteria root) {
		this.criteria = criteria;
		this.root = root;
	}

	public static HibernateEntityCriteria forClass(Class<? extends Entity> entityClass) {
		final CriteriaImpl criteria = new CriteriaImpl(entityClass.getName(), null);
		return new HibernateEntityCriteria(criteria, null);
	}

	public String getAlias() {
		return criteria.getAlias();
	}

	public EntityCriteria root() {
		return root != null ? root : this;
	}

	public HibernateEntityCriteria getCriteria(String path) {
		if (!criterias.containsKey(path)) {
			criterias.put(path, createCriteria(path));
		}
		return criterias.get(path);
	}

	public EntityProperty getProperty(String path) {
		if (!properties.containsKey(path)) {
			properties.put(path, createProperty(path));
		}
		return properties.get(path);
	}

	public void add(EntityCriterion criterion) {
		criteria.add(((HibernateEntityCriterion) criterion).getCriterion());
	}

	public void setPagination(Pagination pagination) {
		if (pagination.getStart() > 0) {
			criteria.setFirstResult(pagination.getStart());
		}
		if (pagination.getCount() >= 0) {
			criteria.setMaxResults(pagination.getCount());
		}
	}

	Criteria assign(Session session) {
		CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;
		criteriaImpl.setSession((SessionImplementor) session);
		return criteriaImpl;
	}

	private HibernateEntityCriteria createCriteria(String path) {
		if (!StringUtils.hasText(path)) {
			throw new IllegalArgumentException("Empty path");
		}
		final int i = path.lastIndexOf('.');
		final String property = i > 0 ? path.substring(i + 1) : path;
		final Criteria subCriteria = criteria.createCriteria(path, allocateName(property));
		return new HibernateEntityCriteria(subCriteria, root());
	}

	private EntityProperty createProperty(String path) {
		if (!StringUtils.hasText(path)) {
			throw new IllegalArgumentException("Empty path");
		}
		final int i = path.lastIndexOf('.');
		final HibernateEntityCriteria entityCriteria = i > 0 ? getCriteria(path.substring(0, i)) : this;
		final String property = i > 0 ? path.substring(i + 1) : path;
		return new HibernateEntityProperty(entityCriteria.criteria, property);
	}

	private String allocateName(String name) {
		String result = name;
		int i = 0;
		while (names.contains(result)) {
			result = name + "_" + i++;
		}
		names.add(result);
		return result;
	}
}
