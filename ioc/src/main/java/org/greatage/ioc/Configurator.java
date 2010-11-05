package org.greatage.ioc;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Configurator<T> {

	boolean supports(Service service);

	void configure(ServiceResources<T> resources);

}
