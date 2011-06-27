/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
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

package org.greatage.inject.internal.proxy;

import org.greatage.inject.services.ProxyFactory;
import org.greatage.inject.services.ServiceBuilder;
import org.greatage.javassist.ClassBuilder;
import org.greatage.javassist.ClassPoolEx;
import org.greatage.util.DescriptionBuilder;
import org.greatage.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This class represents proxy factory implementation using javassist library.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JavassistProxyFactory implements ProxyFactory {
	private static final AtomicLong UID_GENERATOR = new AtomicLong(System.currentTimeMillis());

	private static final String BUILDER_FIELD = "_builder";
	private static final String INTERFACE_FIELD = "_interface";

	private final ClassPoolEx pool;

	/**
	 * Creates new instance of java assist proxy factory with default class pool.
	 */
	public JavassistProxyFactory() {
		this(new ClassPoolEx());
	}

	/**
	 * Creates new instance of java assist proxy factory with specified class pool.
	 *
	 * @param pool class pool
	 */
	public JavassistProxyFactory(final ClassPoolEx pool) {
		this.pool = pool;
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> T createProxy(final ServiceBuilder<T> builder) {
		final Class<T> serviceClass = builder.getMarker().getServiceClass();

		if (!serviceClass.isInterface()) {
			try {
				serviceClass.getConstructor();
			} catch (NoSuchMethodException e) {
				throw new IllegalArgumentException(
						String.format("Object class '%s' should have default constructor", serviceClass), e);
			}
		}

		final Class<T> proxyClass = createProxyClass(builder);
		return ReflectionUtils.newInstance(proxyClass, builder);
	}

	/**
	 * Generates proxy class around specified object builder.
	 *
	 * @return proxy class around specified object builder
	 */
	private <T> Class<T> createProxyClass(final ServiceBuilder<T> builder) {
		final Class<T> objectClass = builder.getMarker().getServiceClass();

		final String className = generateName(objectClass);

		final ClassBuilder<T> classBuilder = new ClassBuilder<T>(pool, className, false, objectClass);

		classBuilder.addField(BUILDER_FIELD, Modifier.PRIVATE | Modifier.FINAL, ServiceBuilder.class);
		classBuilder.addField(INTERFACE_FIELD, Modifier.PRIVATE | Modifier.FINAL, Class.class);

		final String constructorBody = String.format("{ %s = $1; %s = $1.getMarker().getServiceClass(); }",
				BUILDER_FIELD, INTERFACE_FIELD);
		classBuilder.addConstructor(new Class[]{ServiceBuilder.class}, null, constructorBody);

		for (Method method : objectClass.getMethods()) {
			final int modifiers = method.getModifiers();
			if (Modifier.isPublic(modifiers) && !Modifier.isFinal(modifiers)) {
				final String methodName = method.getName();

				final String methodBody;
				if (builder.intercepts(method)) {
					methodBody = new StringBuilder("{ ")
							.append(String.format("final %s _method = %s.getMethod(\"%s\", $sig);",
									Method.class.getName(), INTERFACE_FIELD, methodName))
							.append(String.format("return ($r) %s.invoke(_method, $args);", BUILDER_FIELD))
							.append(" }").toString();
				} else {
					methodBody = String.format("return ($r) ((%s)%s.build()).%s($$);",
							objectClass.getName(), BUILDER_FIELD, methodName);
				}
				classBuilder.addMethod(methodName, Modifier.PUBLIC, method.getReturnType(),
						method.getParameterTypes(), method.getExceptionTypes(), methodBody);
			}
		}

		return classBuilder.build();
	}

	/**
	 * Generates name for proxy class. The name will look like simplified original class name prefixed with <tt>$</tt> and
	 * suffixed with generated unique long number.
	 *
	 * @param inputClass original class
	 * @return generated name for proxy class
	 */
	private String generateName(final Class inputClass) {
		final String uid = Long.toHexString(UID_GENERATOR.getAndIncrement());
		return "$" + inputClass.getSimpleName() + "_" + uid;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("pool", pool);
		return builder.toString();
	}
}
