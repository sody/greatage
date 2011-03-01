/*
 * Copyright 2011 Ivan Khalopik
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
