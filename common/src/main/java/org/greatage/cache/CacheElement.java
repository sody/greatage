package org.greatage.cache;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CacheElement<V> {
	private final V value;
	private final long creationTime;
	private final int timeToLive;
	private int readsToLive;

	public CacheElement(V value, int timeToLive, int readsToLive) {
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
}
