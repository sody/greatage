package org.greatage.domain.internal;

import org.greatage.domain.Entity;
import org.greatage.domain.Query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractQuery<PK extends Serializable, E extends Entity<PK>>
		implements Query<PK, E> {

	private final Class<E> entityClass;

	private Criteria<PK, E> criteria;
	private List<Property> fetches;
	private List<Sort> sorts;

	private int start = 0;
	private int count = -1;

	protected AbstractQuery(final Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	public Query<PK, E> filter(final Criteria<PK, E> criteria) {
		this.criteria = this.criteria != null ?
				this.criteria.and(criteria) :
				criteria;

		return this;
	}

	public Query<PK, E> fetch(final Property property) {
		if (fetches == null) {
			fetches = new ArrayList<Property>();
		}
		fetches.add(property);

		return this;
	}

	public Query<PK, E> sort(final Property property, final boolean ascending, final boolean ignoreCase) {
		if (sorts == null) {
			sorts = new ArrayList<Sort>();
		}
		sorts.add(new Sort(property, ascending, ignoreCase));

		return this;
	}

	public Query<PK, E> paginate(final int start, final int count) {
		this.start = start;
		this.count = count;

		return this;
	}

	public Class<E> getEntityClass() {
		return entityClass;
	}

	public Criteria<PK, E> getCriteria() {
		return criteria;
	}

	public List<Property> getFetches() {
		return fetches;
	}

	public List<Sort> getSorts() {
		return sorts;
	}

	public int getStart() {
		return start;
	}

	public int getCount() {
		return count;
	}

	class Sort {
		private final Property property;
		private final boolean ascending;
		private final boolean ignoreCase;

		Sort(final Property property, final boolean ascending, final boolean ignoreCase) {
			this.property = property;
			this.ascending = ascending;
			this.ignoreCase = ignoreCase;
		}

		public Property getProperty() {
			return property;
		}

		public boolean isAscending() {
			return ascending;
		}

		public boolean isIgnoreCase() {
			return ignoreCase;
		}
	}
}
