/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.hibernate.type;

/**
 * This interface adds functionality for representation enum constants as integers. All enum types that implement it can
 * be used with {@link OrderedEnumUserType} hibernate type.
 *
 * @author Ivan Khalopik
 */
public interface OrderedEnum {

	/**
	 * Gets unique int identifier for enum constant.
	 *
	 * @return unique integer representation of enum constant
	 */
	int getOrder();
}