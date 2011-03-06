/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.greatage.domain;

import org.greatage.util.DescriptionBuilder;

import java.io.Serializable;
import java.util.Collection;

/**
 * This class represents implementation of {@link EntityFilterProcessor} that combines logic of child filter
 * processors.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CompositeFilterProcessor implements EntityFilterProcessor {
	private final Collection<EntityFilterProcessor> processors;

	/**
	 * Constructor with child filter processors initialization.
	 *
	 * @param processors child filter processors
	 */
	public CompositeFilterProcessor(final Collection<EntityFilterProcessor> processors) {
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
