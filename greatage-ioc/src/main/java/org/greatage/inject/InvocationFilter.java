package org.greatage.inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface InvocationFilter {

	boolean supports(Invocation invocation);
}
