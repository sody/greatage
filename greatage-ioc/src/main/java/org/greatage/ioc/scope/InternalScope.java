package org.greatage.ioc.scope;

import org.greatage.ioc.proxy.JdkProxyFactory;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class InternalScope extends GlobalScope {
	public InternalScope() {
		super(new JdkProxyFactory());
	}

	@Override
	public String getName() {
		return ScopeConstants.INTERNAL;
	}
}
