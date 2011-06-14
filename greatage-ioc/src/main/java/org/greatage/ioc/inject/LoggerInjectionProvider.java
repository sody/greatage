package org.greatage.ioc.inject;

import org.greatage.ioc.Marker;
import org.greatage.ioc.logging.Logger;
import org.greatage.ioc.logging.LoggerSource;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LoggerInjectionProvider implements InjectionProvider {
	private final LoggerSource loggerSource;

	public LoggerInjectionProvider(final LoggerSource loggerSource) {
		assert loggerSource != null;

		this.loggerSource = loggerSource;
	}

	public <T> T inject(final Marker<?> marker, final Class<T> resourceClass, final Annotation... annotations) {
		// if resource type is Logger trying to retrieve service specific logger using LoggerSource service
		if (Logger.class.equals(resourceClass)) {
			//TODO: add annotation injection for adding logger of class differ than actual service class?
			final Logger logger = loggerSource.getLogger(marker.getServiceClass());
			return resourceClass.cast(logger);
		}
		return null;
	}
}
