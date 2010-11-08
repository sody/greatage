/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.mock;

import org.greatage.ioc.services.ObjectBuilder;
import org.greatage.util.DescriptionBuilder;
import org.greatage.util.ReflectionUtils;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockObjectBuilder<T> implements ObjectBuilder<T> {
	private final Class<T> interfaceClass;
	private final Class<? extends T> implementationClass;
	private final Object[] constructionParameters;

	public MockObjectBuilder(final Class<T> interfaceClass, final Class<? extends T> implementationClass, final Object... constructionParameters) {
		this.interfaceClass = interfaceClass;
		this.implementationClass = implementationClass;
		this.constructionParameters = constructionParameters;
	}

	public Class<T> getObjectClass() {
		return interfaceClass;
	}

	public T build() {
		return ReflectionUtils.newInstance(implementationClass, constructionParameters);
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("interface", interfaceClass);
		builder.append("implementation", implementationClass);
		return builder.toString();
	}
}
