package org.greatage.domain;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface SessionCallback<V, S> {

	V doInSession(S session) throws Exception;
}
