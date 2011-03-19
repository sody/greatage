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

package org.greatage.domain.sql;

import org.greatage.domain.EntityCriterion;
import org.greatage.domain.EntityProperty;
import org.greatage.util.DescriptionBuilder;

import java.util.Collection;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class SqlProperty implements EntityProperty {

	public EntityCriterion in(Collection values) {
		return null;  //todo: change default method body
	}

	public EntityCriterion in(Object[] values) {
		return null;  //todo: change default method body
	}

	public EntityCriterion like(Object value) {
		return null;  //todo: change default method body
	}

	public EntityCriterion eq(Object value) {
		return null;  //todo: change default method body
	}

	public EntityCriterion ne(Object value) {
		return null;  //todo: change default method body
	}

	public EntityCriterion gt(Object value) {
		return null;  //todo: change default method body
	}

	public EntityCriterion lt(Object value) {
		return null;  //todo: change default method body
	}

	public EntityCriterion le(Object value) {
		return null;  //todo: change default method body
	}

	public EntityCriterion ge(Object value) {
		return null;  //todo: change default method body
	}

	public EntityCriterion between(Object min, Object max) {
		return null;  //todo: change default method body
	}

	public EntityCriterion eqProperty(EntityProperty other) {
		return null;  //todo: change default method body
	}

	public EntityCriterion neProperty(EntityProperty other) {
		return null;  //todo: change default method body
	}

	public EntityCriterion leProperty(EntityProperty other) {
		return null;  //todo: change default method body
	}

	public EntityCriterion geProperty(EntityProperty other) {
		return null;  //todo: change default method body
	}

	public EntityCriterion ltProperty(EntityProperty other) {
		return null;  //todo: change default method body
	}

	public EntityCriterion gtProperty(EntityProperty other) {
		return null;  //todo: change default method body
	}

	public EntityCriterion isNull() {
		return null;  //todo: change default method body
	}

	public EntityCriterion isNotNull() {
		return null;  //todo: change default method body
	}

	public EntityCriterion isEmpty() {
		return null;  //todo: change default method body
	}

	public EntityCriterion isNotEmpty() {
		return null;  //todo: change default method body
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		return builder.toString();
	}
}
