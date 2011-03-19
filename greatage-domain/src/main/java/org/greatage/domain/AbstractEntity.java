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
 * This class represents implementation of utility methods of {@link org.greatage.domain.Entity}.
 *
 * @author Ivan Khalopik
 * @param <PK> type of entity primary key
 * @since 1.0
 */
public abstract class AbstractEntity<PK extends Serializable> implements Entity<PK> {

	public boolean isNew() {
		return getId() == null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !(o instanceof Entity)) {
			return false;
		}
		final Entity entity = (Entity) o;
		return !(getId() != null ? !getId().equals(entity.getId()) : entity.getId() != null);
	}

	@Override
	public int hashCode() {
		return getId() == null ? super.hashCode() : getId().hashCode();
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("id", isNew() ? "new" : getId());
		return builder.toString();
	}

}
