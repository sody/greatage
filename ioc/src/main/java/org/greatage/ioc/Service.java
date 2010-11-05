package org.greatage.ioc;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Service<T> {

	String getServiceId();

	Class<T> getServiceClass();

	boolean isOverride();

	boolean isLazy();

	String getScope();

	T build(ServiceResources<T> resources);

}
