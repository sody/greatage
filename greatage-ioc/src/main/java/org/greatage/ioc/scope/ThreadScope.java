/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.scope;

import org.greatage.util.CollectionUtils;

import java.util.Map;

/**
 * This class represents {@link Scope} implementation that is used for services that have the same state inside one
 * application thread. Default scope identifier is {@link ScopeConstants#THREAD}.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ThreadScope extends AbstractScope {
	private final ThreadLocal<Map<String, Object>> services = new ThreadLocal<Map<String, Object>>() {
		@Override
		protected Map<String, Object> initialValue() {
			return CollectionUtils.newConcurrentMap();
		}
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void cleanup() {
		super.cleanup();
		services.remove();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Map<String, Object> getServices() {
		return services.get();
	}
}
