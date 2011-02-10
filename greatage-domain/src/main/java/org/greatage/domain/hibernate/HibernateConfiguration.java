/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.hibernate;

import org.hibernate.cfg.Configuration;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface HibernateConfiguration {

	void configure(Configuration configuration);

}
