/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain;

import java.io.Serializable;

/**
 * Representation of persistent object
 *
 * @author Ivan Khalopik
 * @param <PK>       type of entity primary key
 * @since 1.0
 */
public interface Entity<PK extends Serializable> extends Serializable {

	public static final String ID_PROPERTY = "id";

	/**
	 * Gets entity unique identifier.
	 *
	 * @return entity identifier
	 */
	PK getId();

	/**
	 * Determines if entity is persistent.
	 *
	 * @return false if entity state is not persistent
	 */
	boolean isNew();

}
