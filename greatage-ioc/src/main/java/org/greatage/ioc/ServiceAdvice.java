package org.greatage.ioc;

import org.greatage.ioc.proxy.Interceptor;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public interface ServiceAdvice {

	ServiceAdviceOptions add(Interceptor interceptor, String orderId, String... constraints);

	ServiceAdviceOptions addInstance(Class<? extends Interceptor> interceptorClass, String orderId, String... constraints);
}
