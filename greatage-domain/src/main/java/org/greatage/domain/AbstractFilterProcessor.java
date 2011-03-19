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

/**
 * This class represents abstract implementation of {@link EntityFilterProcessor} that works only with filters of
 * specified class.
 *
 * @author Ivan Khalopik
 * @param <PK>       type of entities primary key
 * @param <E>        type of entities
 * @param <F>        type of entities filter
 * @since 1.0
 */
public abstract class AbstractFilterProcessor<PK extends Serializable, E extends Entity<PK>, F extends EntityFilter<PK, E>>
		implements EntityFilterProcessor {

	private final Class<? extends Entity> supportedEntityClass;

	/**
	 * Constructor with supported entity class initialization.
	 *
	 * @param supportedEntityClass supported entity class
	 */
	protected AbstractFilterProcessor(final Class<? extends Entity> supportedEntityClass) {
		this.supportedEntityClass = supportedEntityClass;
	}

	/**
	 * Executes some logic to inject into entity criteria parameters stored in filter, if specified filter are supported by
	 * this filter processor.
	 *
	 * @param criteria   entity criteria
	 * @param filter	 entity filter
	 * @param pagination pagination
	 * @param <SPK>      type of entities primary key
	 * @param <SE>       type of entities
	 */
	@SuppressWarnings({"unchecked"})
	public <SPK extends Serializable, SE extends Entity<SPK>>
	void process(final EntityCriteria criteria, final EntityFilter<SPK, SE> filter, final Pagination pagination) {
		if (supports(filter)) {
			processFilter(criteria, (F) filter);
		}
	}

	/**
	 * Determines if this filter processor supports specified filter. If not, it will not be processed.
	 *
	 * @param filter entity filter
	 * @param <SPK>  type of entities primary key
	 * @param <SE>   type of entities
	 * @return true if filter is supported, false otherwise
	 */
	protected <SPK extends Serializable, SE extends Entity<SPK>>
	boolean supports(final EntityFilter<SPK, SE> filter) {
		return supportedEntityClass.isAssignableFrom(filter.getEntityClass());
	}

	/**
	 * Executes some logic to inject into entity criteria parameters stored in filter.
	 *
	 * @param criteria entity criteria
	 * @param filter   processed entity filter
	 */
	protected abstract void processFilter(final EntityCriteria criteria, final F filter);

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(supportedEntityClass);
		return builder.toString();
	}
}
