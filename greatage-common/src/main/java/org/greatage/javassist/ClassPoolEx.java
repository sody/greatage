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

package org.greatage.javassist;

import javassist.ClassPool;
import javassist.LoaderClassPath;
import org.greatage.util.CollectionUtils;

import java.util.Set;

/**
 * This class represent javassist class pool fix that works on web application class loaders.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ClassPoolEx extends ClassPool {
	private static final Set<ClassLoader> CLASS_LOADERS = CollectionUtils.newConcurrentSet();

	/**
	 * Creates default javassist class pool with fix that works on web application class loaders.
	 */
	public ClassPoolEx() {
		super(true);
	}

	/**
	 * Adds missing class loader classpath for specified class.
	 *
	 * @param clazz class
	 */
	public void importClass(final Class clazz) {
		final ClassLoader loader = clazz.getClassLoader();
		if (loader != null && !CLASS_LOADERS.contains(loader)) {
			CLASS_LOADERS.add(loader);
			insertClassPath(new LoaderClassPath(loader));
		}
	}
}
