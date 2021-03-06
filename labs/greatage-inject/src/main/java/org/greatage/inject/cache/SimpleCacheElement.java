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

package org.greatage.inject.cache;

import org.greatage.util.DescriptionBuilder;

/**
 * This class represents implementation of cache element for {@link SimpleCache} with element expiration logic.
 *
 * @param <V> type of cache items
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SimpleCacheElement<V> {
	private final V value;
	private final long creationTime;
	private final int timeToLive;
	private int readsToLive;

	/**
	 * Creates new instance of cache element for {@link SimpleCache} with defined element value, time to live and reads to
	 * live.
	 *
	 * @param value	   cache element value
	 * @param timeToLive  time before cache item will be marked as expired and then deleted
	 * @param readsToLive number of reads before cache item will be marked as expired and then deleted
	 */
	public SimpleCacheElement(final V value, final int timeToLive, final int readsToLive) {
		this.value = value;
		this.timeToLive = timeToLive;
		this.readsToLive = readsToLive;
		creationTime = System.currentTimeMillis();
	}

	/**
	 * Gets cache item value. It also increases element reads count.
	 *
	 * @return cache item value
	 */
	public V getValue() {
		if (readsToLive > 0) {
			readsToLive--;
		}
		return value;
	}

	/**
	 * Checks if cache element is marked as expired. It marks as expired when it
	 *
	 * @return true if element is marked as expired, false otherwise
	 */
	public boolean isExpired() {
		return readsToLive == 0 || (timeToLive > 0 && creationTime + timeToLive < System.currentTimeMillis());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("value", value);
		builder.append("created", creationTime);
		builder.append("timeToLive", timeToLive);
		builder.append("readsToLive", readsToLive);
		builder.append("expired", isExpired());
		return builder.toString();
	}
}
