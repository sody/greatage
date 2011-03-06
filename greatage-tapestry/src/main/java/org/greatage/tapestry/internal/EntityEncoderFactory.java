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
