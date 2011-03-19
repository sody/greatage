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

import java.io.Serializable;
import java.util.Collection;

/**
 * This interface represents base filter for selection entities from repositories.
 *
 * @author Ivan Khalopik
 * @param <PK>       type of entities primary key
 * @param <E>        type of entities
 * @see EntityRepository
 * @see EntityService
 * @since 1.0
 */
public interface EntityFilter<PK extends Serializable, E extends Entity<PK>> {

	/**
	 * Gets entity class.
	 *
	 * @return entity class
	 */
	Class<E> getEntityClass();

	/**
	 * Gets collection of entity primary keys that will be included into select result.
	 *
	 * @return collection of entity primary keys that will be included into select result
	 */
	Collection<PK> getIncludeKeys();

	/**
	 * Gets collection of entity primary keys that will be excluded from select result.
	 *
	 * @return collection of entity primary keys that will be excluded from select result
	 */
	Collection<PK> getExcludeKeys();

	/**
	 * Gets search query string for entity full-text search.
	 *
	 * @return search query string for entity full-text search
	 */
	String getQueryString();

}
