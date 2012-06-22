package org.greatage.domain.internal;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractSessionManager<S> implements SessionManager<S> {
	private final ThreadLocal<S> sessionHolder = new ThreadLocal<S>();

	public <V> V execute(final Callback<V, S> callback) {
		S session = sessionHolder.get();
		final boolean sessionCreated = session == null;
		try {
			if (session == null) {
				session = openSession();
				sessionHolder.set(session);
			}
			final V result = callback.doInSession(session);
			flushSession(session);
			return result;
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (sessionCreated && session != null) {
				closeSession(session);
				sessionHolder.remove();
			}
		}
	}

	protected abstract S openSession();

	protected abstract void flushSession(S session);

	protected abstract void closeSession(S session);
}
