/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.hibernate.type;

/**
 * This interface adds functionality for representation enum constants as strings. All enum types that implement it can
 * be used with {@link NamedEnumUserType} hibernate type.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface NamedEnum {

	/**
	 * Gets unique name for enum constant.
	 *
	 * @return unique string representation of enum constant
	 */
	String getName();
}
