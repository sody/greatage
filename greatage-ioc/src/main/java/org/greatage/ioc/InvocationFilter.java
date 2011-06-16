package org.greatage.ioc;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface InvocationFilter {

	boolean supports(Invocation invocation);
}
