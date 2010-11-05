/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */
package org.greatage.domain.search.hibernate;

import org.greatage.domain.Entity;
import org.greatage.domain.repository.EntityFilter;
import org.apache.lucene.search.BooleanQuery;

import java.io.Serializable;

/**
 * This interface represents search processing logic to inject it into lucene query.
 *
 * @author Ivan Khalopik
 * @see HibernateSearchEngine
 * @see AbstractSearchProcessor
 * @see CompositeSearchProcessor
 */
public interface SearchProcessor {

	/**
	 * Executes some logic to inject into lucene boolean query parameters stored in filter.
	 *
	 * @param query  lucene boolean query
	 * @param filter processed entity filter
	 * @param <PK>   type of entities primary key
	 * @param <E>    type of entities
	 */
	<PK extends Serializable, E extends Entity<PK>>
	void process(BooleanQuery query, EntityFilter<PK, E> filter);

}
