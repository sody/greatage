package org.greatage.ioc;

import org.greatage.ioc.proxy.Invocation;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public interface InvocationFilter {

	boolean supports(Invocation invocation);
}
