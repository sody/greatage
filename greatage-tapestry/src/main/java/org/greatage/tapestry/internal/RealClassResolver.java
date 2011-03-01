/*
 * Copyright 2011 Ivan Khalopik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
