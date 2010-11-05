package org.greatage.ioc.internal.proxy;

import org.greatage.ioc.services.ObjectBuilder;
import org.greatage.ioc.services.ProxyFactory;
import org.greatage.util.CommonMessages;
import org.greatage.util.DescriptionBuilder;

/**
 * This class represents abstract proxy factory representation that helps to validate input parameters for creation
 * proxy.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractProxyFactory implements ProxyFactory {

	/**
	 * Checks if proxy instance can be created for specified class. It can not be created when original class is not
	 * interface and has no default constructor.
	 *
	 * @param builder object builder
	 * @throws IllegalArgumentException if proxy instance can not be created
	 */
	protected <T> void validate(final ObjectBuilder<T> builder) {
		if (builder == null) {
			throw new IllegalArgumentException(CommonMessages.nullObjectBuilder());
		}
		final Class<T> proxyClass = builder.getObjectClass();
		if (proxyClass == null) {
			throw new IllegalArgumentException(CommonMessages.nullProxyClass());
		}
		if (!proxyClass.isInterface()) {
			try {
				proxyClass.getConstructor();
			} catch (NoSuchMethodException e) {
				throw new IllegalArgumentException(CommonMessages.proxyClassMustHaveDefaultConstructor(proxyClass), e);
			}
		}
	}

	@Override
	public String toString() {
		return new DescriptionBuilder(getClass()).toString();
	}
}
