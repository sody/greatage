/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.internal;

import org.apache.tapestry5.ioc.annotations.UsesConfiguration;
import org.greatage.tapestry.services.ClassResolver;

import java.util.Collection;

/**
 * @author Ivan Khalopik
 */
@UsesConfiguration(ClassResolver.class)
public class RealClassResolver {
	private final Collection<ClassResolver> classResolvers;

	public RealClassResolver(Collection<ClassResolver> classResolvers) {
		this.classResolvers = classResolvers;
	}

	<T> Class<T> getRealClass(Class<T> clazz) {
		for (ClassResolver classResolver : classResolvers) {
			final Class<T> realClass = classResolver.getRealClass(clazz);
			if (realClass != null) {
				return realClass;
			}
		}
		return clazz;
	}
}
