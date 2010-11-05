/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.search;

import org.greatage.domain.Entity;
import org.greatage.domain.Pagination;
import org.greatage.domain.repository.EntityCriteria;
import org.greatage.domain.repository.EntityFilter;
import org.greatage.domain.repository.EntityFilterProcessor;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents implementation of {@link org.greatage.domain.repository.EntityFilterProcessor} that processes search
 * mechanism. It delegates work to search engine, searches for primary keys and adds them to entity criteria.
 *
 * @author Ivan Khalopik
 * @see SearchEngine
 */
public class SearchFilterProcessor implements EntityFilterProcessor {
	private final SearchEngine searchEngine;

	/**
	 * Constructor with search engine initialization.
	 *
	 * @param searchEngine search engine
	 */
	public SearchFilterProcessor(SearchEngine searchEngine) {
		this.searchEngine = searchEngine;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("SearchFilterProcessor(");
		sb.append("searchEngine=").append(searchEngine);
		sb.append(")");
		return super.toString();
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void process(EntityCriteria criteria, EntityFilter<PK, E> filter, Pagination pagination) {
		final List<PK> pks = searchEngine.search(filter);
		if (pks != null) {
			criteria.add(criteria.getProperty(Entity.ID_PROPERTY).in(pks));
		}
	}
}
