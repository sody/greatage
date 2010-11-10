/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
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
