/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.cache;

import org.greatage.util.DescriptionBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SimpleCacheElement<V> {
	private final V value;
	private final long creationTime;
	private final int timeToLive;
	private int readsToLive;

	public SimpleCacheElement(final V value, final int timeToLive, final int readsToLive) {
		this.value = value;
		this.timeToLive = timeToLive;
		this.readsToLive = readsToLive;
		creationTime = currentTime();
	}

	public V getValue() {
		if (readsToLive > 0) {
			readsToLive--;
		}
		return value;
	}

	public boolean isExpired() {
		return readsToLive == 0 || (timeToLive > 0 && creationTime + timeToLive < currentTime());
	}

	private long currentTime() {
		return System.currentTimeMillis();
	}

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
