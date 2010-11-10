/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.internal;

import java.io.Serializable;

/**
 * @author Ivan Khalopik
 */
public class PersistedValue implements Serializable {
	private final String className;
	private final String value;

	public PersistedValue(Class clazz, String value) {
		this(clazz.getName(), value);
	}

	public PersistedValue(String className, String value) {
		this.className = className;
		this.value = value;
	}

	public Class getValueClass() {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	public String getValue() {
		return value;
	}
}
