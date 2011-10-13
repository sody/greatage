package org.greatage.db.gae;

import org.greatage.db.ChangeBuilder;
import org.greatage.db.ChangeSetBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class GAEChange implements ChangeBuilder, DataStoreCallback<Object> {
	private final GAEChangeSet changeSet;

	GAEChange(final GAEChangeSet changeSet) {
		this.changeSet = changeSet;
	}

	public ChangeSetBuilder end() {
		return changeSet.endChange(this);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
