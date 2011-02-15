/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.scope;

import org.greatage.util.CollectionUtils;

import java.util.Map;

/**
 * This class represents {@link Scope} implementation that is used for services that have the same state for whole
 * application. Default scope identifier is {@link ScopeConstants#GLOBAL}.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GlobalScope extends AbstractScope {
	private final Map<String, Object> services = CollectionUtils.newConcurrentMap();

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Map<String, Object> getServices() {
		return services;
	}
}
