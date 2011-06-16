/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
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

package org.greatage.ioc.internal.coerce;

/**
 * This class represents {@link org.greatage.ioc.services.Coercion} implementation that converts values from boolean to string.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BooleanToStringCoercion extends AbstractCoercion<Boolean, String> {

	/**
	 * Creates new coercion instance that converts values from boolean to string.
	 */
	public BooleanToStringCoercion() {
		super(Boolean.class, String.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public String coerce(final Boolean source) {
		return source.toString();
	}
}
