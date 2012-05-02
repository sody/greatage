package org.greatage.domain.internal;

import org.greatage.domain.Entity;
import org.greatage.domain.Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractQuery<PK extends Serializable, E extends Entity<PK>>
		implements Repository.Query<PK, E> {

	private final Class<E> entityClass;

	private Repository.Criteria<PK, E> criteria;
	private List<Repository.Property> fetches;

	private int start = 0;
	private int count = -1;

	protected AbstractQuery(final Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	public Repository.Query<PK, E> filter(final Repository.Criteria<PK, E> criteria) {
		this.criteria = this.criteria != null ?
				this.criteria.and(criteria) :
				criteria;

		return this;
	}

	public Repository.Query<PK, E> fetch(final Repository.Property property) {
		if (fetches == null) {
			fetches = new ArrayList<Repository.Property>();
		}
		fetches.add(property);

		return this;
	}

	public Repository.Query<PK, E> sort(final Repository.Property property, final boolean ascending, final boolean ignoreCase) {
		throw new UnsupportedOperationException();
	}

	public Repository.Query<PK, E> map(final Repository.Property property, final String key) {
		throw new UnsupportedOperationException();
	}

	public Repository.Query<PK, E> paginate(final int start, final int count) {
		this.start = start;
		this.count = count;

		return this;
	}

	public Class<E> getEntityClass() {
		return entityClass;
	}

	public Repository.Criteria<PK, E> getCriteria() {
		return criteria;
	}

	public List<Repository.Property> getFetches() {
		return fetches;
	}

	public int getStart() {
		return start;
	}

	public int getCount() {
		return count;
	}
}
