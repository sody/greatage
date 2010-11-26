package org.greatage.security;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AnonymousAuthenticationProvider<T extends Authentication>
		extends AbstractAuthenticationProvider<T, AnonymousAuthenticationToken> {

	public AnonymousAuthenticationProvider() {
		super(AnonymousAuthenticationToken.class);
	}

	@Override
	protected T doSignIn(final AnonymousAuthenticationToken token) {
		return getAntonymousAuthentication();
	}

	protected abstract T getAntonymousAuthentication();

}
