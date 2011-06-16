package org.greatage.inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ServiceAdvice {

	ServiceAdviceOptions add(Interceptor interceptor, String orderId, String... constraints);

	ServiceAdviceOptions addInstance(Class<? extends Interceptor> interceptorClass, String orderId,
									 String... constraints);
}
