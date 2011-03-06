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

import java.util.Collection;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface EntityProperty {

	String getProperty();

	EntityCriterion in(Collection values);

	EntityCriterion in(Object[] values);

	EntityCriterion like(Object value);

	EntityCriterion eq(Object value);

	EntityCriterion ne(Object value);

	EntityCriterion gt(Object value);

	EntityCriterion lt(Object value);

	EntityCriterion le(Object value);

	EntityCriterion ge(Object value);

	EntityCriterion between(Object min, Object max);

	EntityCriterion eqProperty(EntityProperty other);

	EntityCriterion neProperty(EntityProperty other);

	EntityCriterion leProperty(EntityProperty other);

	EntityCriterion geProperty(EntityProperty other);

	EntityCriterion ltProperty(EntityProperty other);

	EntityCriterion gtProperty(EntityProperty other);

	EntityCriterion isNull();

	EntityCriterion isNotNull();

	EntityCriterion isEmpty();

	EntityCriterion isNotEmpty();

	void sort();

	void sort(boolean ascending);

	void sort(boolean ascending, boolean ignoreCase);

}
