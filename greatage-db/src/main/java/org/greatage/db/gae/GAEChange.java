package org.greatage.db.gae;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class GAEChange implements DataStoreCallback {

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
