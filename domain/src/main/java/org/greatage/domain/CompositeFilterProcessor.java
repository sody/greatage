/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain;

import org.greatage.util.DescriptionBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents implementation of {@link EntityFilterProcessor} that combines logic of child filter
 * processors.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CompositeFilterProcessor implements EntityFilterProcessor {
	private final List<EntityFilterProcessor> processors;

	/**
	 * Constructor with child filter processors initialization.
	 *
	 * @param processors child filter processors
	 */
	public CompositeFilterProcessor(final List<EntityFilterProcessor> processors) {
		this.processors = processors;
	}

	public <PK extends Serializable, E extends Entity<PK>>
	void process(final EntityCriteria criteria, final EntityFilter<PK, E> filter, final Pagination pagination) {
		for (EntityFilterProcessor processor : processors) {
			processor.process(criteria, filter, pagination);
		}
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(processors);
		return builder.toString();
	}
}
