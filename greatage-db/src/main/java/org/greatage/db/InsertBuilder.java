package org.greatage.db;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface InsertBuilder extends ChangeBuilder {

	InsertBuilder set(String propertyName, Object value);

}
