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
