/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.search.hibernate;

import org.greatage.domain.Entity;
import org.greatage.domain.repository.EntityFilter;
import org.apache.lucene.search.BooleanQuery;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents implementation of {@link SearchProcessor} that combines logic of child search processors.
 *
 * @author Ivan Khalopik
 */
public class CompositeSearchProcessor implements SearchProcessor {
	private final List<SearchProcessor> processors;

	/**
	 * Constructor with child search processors initialization.
	 *
	 * @param processors child search processors
	 */
	public CompositeSearchProcessor(List<SearchProcessor> processors) {
		this.processors = processors;
	}

	public <PK extends Serializable, E extends Entity<PK>> void process(BooleanQuery query, EntityFilter<PK, E> filter) {
		for (SearchProcessor processor : processors) {
			processor.process(query, filter);
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("CompositeSearchProcessor[");
		for (SearchProcessor processor : processors) {
			sb.append(processor).append(",");
		}
		sb.append("]");
		return sb.toString();
	}
}
