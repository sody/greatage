package org.greatage.security;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractAuthenticationProvider<T extends Authentication, E extends AuthenticationToken>
		implements AuthenticationProvider<T> {

	private final Class<E> supportedTokenClass;

	protected AbstractAuthenticationProvider(final Class<E> supportedTokenClass) {
		this.supportedTokenClass = supportedTokenClass;
	}

	@SuppressWarnings({"unchecked"})
	public T signIn(final AuthenticationToken token) throws AuthenticationException {
		if (supports(token)) {
			return doSignIn((E) token);
		}

		return null;
	}

	public void signOut(final T authentication) throws AuthenticationException {
		//do nothing by default
	}

	protected boolean supports(final AuthenticationToken token) {
		return token != null && supportedTokenClass.isAssignableFrom(token.getClass());
	}

	protected abstract T doSignIn(final E token);

}
