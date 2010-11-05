package org.greatage.ioc;

import org.greatage.ioc.services.Logger;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ServiceLocator {

	<T> T getService(String id, Class<T> serviceClass);

	<T> T getService(Class<T> serviceClass);

	<T> List<T> findServices(Class<T> serviceClass);

	Logger getLogger();

}
