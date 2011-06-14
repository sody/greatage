package org.greatage.ioc.inject;

import org.greatage.ioc.Marker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LoggerInjectionProvider implements InjectionProvider {

	public <T> T inject(final Marker<?> marker, final Class<T> resourceClass, final Annotation... annotations) {
		// if resource type is Logger trying to retrieve service specific logger using slf4j
		if (Logger.class.equals(resourceClass)) {
			//TODO: add annotation injection for adding logger of class differ than actual service class?
			final Logger logger = LoggerFactory.getLogger(marker.getServiceClass());
			return resourceClass.cast(logger);
		}
		return null;
	}
}
