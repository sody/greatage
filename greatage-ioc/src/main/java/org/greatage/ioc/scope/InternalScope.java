package org.greatage.ioc.scope;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class InternalScope extends GlobalScope {
	@Override
	public String getName() {
		return ScopeConstants.INTERNAL;
	}
}
