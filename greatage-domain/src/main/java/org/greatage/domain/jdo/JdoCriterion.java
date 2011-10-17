package org.greatage.domain.jdo;

import org.greatage.domain.EntityCriterion;
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
