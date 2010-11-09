/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.repository.sql;

import org.greatage.domain.repository.EntityCriterion;
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
