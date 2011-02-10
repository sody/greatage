/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
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
