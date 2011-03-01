/*
 * Copyright 2011 Ivan Khalopik
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

package org.greatage.tapestry.select;

import org.greatage.domain.Entity;
import org.greatage.domain.EntityService;

import java.io.Serializable;
import java.util.List;

/**
 * Needs ga:ga-core dependency
 *
 * @author Ivan Khalopik
 */
public class EntitySelectModel<PK extends Serializable, E extends Entity<PK>>
		extends LazySelectModel<E> {

	private final EntityService<PK, E> entityService;

	public EntitySelectModel(EntityService<PK, E> entityService) {
		this.entityService = entityService; //todo: add defence support
	}

	@Override
	public List<E> getValues() {
		return entityService.getEntities();
	}
}
