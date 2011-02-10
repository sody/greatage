/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.hibernate;

import org.hibernate.cfg.Configuration;

import java.util.Collection;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HibernateAnnotationConfiguration implements HibernateConfiguration {
	private final Collection<Class> annotatedClasses;

	public HibernateAnnotationConfiguration(final Collection<Class> annotatedClasses) {
		this.annotatedClasses = annotatedClasses;
	}

	public void configure(final Configuration configuration) {
		for (Class annotatedClass : annotatedClasses) {
			configuration.addAnnotatedClass(annotatedClass);
		}
	}
}
