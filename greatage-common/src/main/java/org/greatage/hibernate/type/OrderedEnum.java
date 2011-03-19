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