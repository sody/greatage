/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.javassist;

import javassist.ClassPool;
import javassist.LoaderClassPath;
import org.greatage.util.CollectionUtils;

import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ClassPoolEx extends ClassPool {
	private static final Set<ClassLoader> CLASS_LOADERS = CollectionUtils.newConcurrentSet();

	public ClassPoolEx() {
		super(true);
	}

	public void importClass(final Class clazz) {
		final ClassLoader loader = clazz.getClassLoader();
		if (loader != null && !CLASS_LOADERS.contains(loader)) {
			CLASS_LOADERS.add(loader);
			insertClassPath(new LoaderClassPath(loader));
		}
	}
}
