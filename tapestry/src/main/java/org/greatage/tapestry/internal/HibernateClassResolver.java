/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.internal;

import org.greatage.hibernate.HibernateUtils;
import org.greatage.tapestry.services.ClassResolver;

/**
 * Needs ga:ga-core and org.hibernate:hibernate-core dependencies.
 *
 * @author Ivan Khalopik
 */
public class HibernateClassResolver implements ClassResolver {

	public <T> Class<T> getRealClass(Class<T> clazz) {
		return HibernateUtils.getRealClass(clazz);
	}

}
