package org.greatage.domain;

import java.io.Serializable;

/**
 * This class represents implementation of utility methods of {@link org.greatage.domain.Entity}.
 *
 * @author Ivan Khalopik
 * @param <PK> type of entity primary key
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
		final StringBuilder sb = new StringBuilder(getClass().getSimpleName());
		sb.append("#").append(isNew() ? "new" : getId());
		return sb.toString();
	}

}
