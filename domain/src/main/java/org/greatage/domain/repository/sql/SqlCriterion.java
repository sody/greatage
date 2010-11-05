package org.greatage.domain.repository.sql;

import org.greatage.domain.repository.EntityCriterion;

/**
 * @author Ivan Khalopik
 */
public class SqlCriterion implements EntityCriterion {
	private final String sql;

	public SqlCriterion(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}

	public EntityCriterion or(EntityCriterion... criterions) {
		final StringBuilder sb = new StringBuilder(getSql());
		for (EntityCriterion criterion : criterions) {
			sb.append(" or ");
			sb.append(((SqlCriterion) criterion).getSql());
		}
		return new SqlCriterion(sb.toString());
	}

	public EntityCriterion and(EntityCriterion... criterions) {
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

}
