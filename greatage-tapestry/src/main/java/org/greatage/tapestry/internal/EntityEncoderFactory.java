/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.internal;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.greatage.domain.Entity;
import org.greatage.domain.EntityRepository;

import java.io.Serializable;

/**
 * Needs ga:ga-core dependency.
 *
 * @author Ivan Khalopik
 */
public class EntityEncoderFactory<PK extends Serializable> implements ValueEncoderFactory<Entity<PK>> {
	private final TypeCoercer typeCoercer;
	private final EntityRepository repository;
	private final Class<PK> pkClass;

	public EntityEncoderFactory(TypeCoercer typeCoercer, EntityRepository repository, Class<PK> pkClass) {
		this.repository = repository; //todo: defence
		this.typeCoercer = typeCoercer; //todo: defence
		this.pkClass = pkClass; //todo: defence
	}

	public ValueEncoder<Entity<PK>> create(final Class<Entity<PK>> type) {
		return new ValueEncoder<Entity<PK>>() {
			public String toClient(Entity<PK> value) {
				return value != null ? typeCoercer.coerce(value.getId(), String.class) : null;
			}

			public Entity<PK> toValue(String clientValue) {
				final PK pk = typeCoercer.coerce(clientValue, pkClass);
				return pk != null ? repository.get(type, pk) : null;
			}

			@Override
			public String toString() {
				final StringBuilder sb = new StringBuilder("EntityValueEncoder(");
				sb.append("class=").append(type);
				sb.append(")");
				return sb.toString();
			}
		};
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("EntityEncoderFactory(");
		sb.append("pkClass=").append(pkClass);
		sb.append(")");
		return sb.toString();
	}
}
