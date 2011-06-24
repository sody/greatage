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

import org.greatage.inject.Interceptor;
import org.greatage.inject.Invocation;
import org.greatage.inject.services.ObjectBuilder;
import org.greatage.inject.services.ProxyFactory;
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
	private static final String INTERCEPTOR_FIELD = "_interceptor";
	private static final String INTERCEPT_METHOD = "_intercept";

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
	public <T> T createProxy(final Class<T> objectClass, final ObjectBuilder<T> builder,
							 final Interceptor interceptor) {
		assert builder != null : "Object builder should be specified";
		assert objectClass != null : "Object class should be specified";
		if (!objectClass.isInterface()) {
			try {
				objectClass.getConstructor();
			} catch (NoSuchMethodException e) {
				throw new IllegalArgumentException(
						String.format("Object class '%s' should have default constructor", objectClass), e);
			}
		}

		final Class<T> proxyClass = createProxyClass(objectClass, interceptor != null);
		return ReflectionUtils.newInstance(proxyClass, builder, interceptor);
	}

	/**
	 * Generates proxy class around specified object builder.
	 *
	 * @return proxy class around specified object builder
	 */
	private <T> Class<T> createProxyClass(final Class<T> objectClass, final boolean intercepted) {
		final String className = generateName(objectClass);

		final ClassBuilder<T> classBuilder = new ClassBuilder<T>(pool, className, false, objectClass);

		classBuilder.addField(BUILDER_FIELD, Modifier.PRIVATE | Modifier.FINAL, ObjectBuilder.class);
		classBuilder.addField(INTERCEPTOR_FIELD, Modifier.PRIVATE | Modifier.FINAL, Interceptor.class);

		final String constructorBody = String.format("{ %s = $1; %s = $2; }", BUILDER_FIELD, INTERCEPTOR_FIELD);
		classBuilder.addConstructor(new Class[]{ObjectBuilder.class, Interceptor.class}, null, constructorBody);

		if (intercepted) {
			final String interceptBody = new StringBuilder("{ ")
					.append(String.format("final %s _invocation = new %s($1, $2);",
							Invocation.class.getName(), InvocationImpl.class.getName()))
					.append(String.format("return ($r) %s.invoke(_invocation, $3);", INTERCEPTOR_FIELD))
					.append(" }").toString();
			classBuilder.addMethod(INTERCEPT_METHOD, Modifier.PRIVATE, Object.class,
					new Class[]{Object.class, Method.class, Object[].class}, null, interceptBody);
		}

		for (Method method : objectClass.getMethods()) {
			final int modifiers = method.getModifiers();
			if (Modifier.isPublic(modifiers) && !Modifier.isFinal(modifiers)) {
				final String methodName = method.getName();
				final String methodBody;
				if (intercepted) {
					methodBody = new StringBuilder("{ ")
							.append(String.format("final %s _target = %s.build();",
									Object.class.getName(), BUILDER_FIELD))
							.append(String.format("final %s _method = _target.getClass().getMethod(\"%s\", $sig);",
									Method.class.getName(), methodName))
							.append(String.format("return ($r) %s(_target, _method, $args);", INTERCEPT_METHOD))
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
