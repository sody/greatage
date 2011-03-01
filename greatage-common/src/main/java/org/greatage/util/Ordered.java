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

package org.greatage.util;

import java.util.List;

/**
 * This interface represents item that can be ordered according to its order identifier and constraints.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Ordered {

	/**
	 * Prefix that indicates that this item must be before item specified in suffix.
	 */
	String BEFORE = "before:";

	/**
	 * Prefix that indicates that this item must be after item specified in suffix.
	 */
	String AFTER = "after:";

	/**
	 * Gets order identifier.
	 *
	 * @return order identifier
	 */
	String getOrderId();

	/**
	 * Gets list of order constraints. They must be like <tt>after:<another order id></tt>.
	 *
	 * @return list of order constraints or empty list
	 */
	List<String> getOrderConstraints();
}
