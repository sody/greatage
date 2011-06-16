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

package org.greatage.ioc.internal.proxy;

import org.greatage.ioc.services.ObjectBuilder;
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
public class JavassistProxyFactory extends AbstractProxyFactory {
	private static final AtomicLong UID_GENERATOR = new AtomicLong(System.currentTimeMillis());

	private static final String HANDLER_FIELD = "_handler";

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
	public <T> T createProxy(final ObjectBuilder<T> builder) {
		validate(builder);

		final Class<T> proxyClass = createProxyClass(builder);
		return ReflectionUtils.newInstance(proxyClass, builder);
	}

	/**
	 * Generates proxy class around specified object builder.
	 *
	 * @param builder object builder
	 * @return proxy class around specified object builder
	 */
	private <T> Class<T> createProxyClass(final ObjectBuilder<T> builder) {
		final Class<T> proxyClass = builder.getObjectClass();
		final String className = generateName(proxyClass);

		final ClassBuilder<T> classBuilder = new ClassBuilder<T>(pool, className, false, proxyClass);

		classBuilder.addField(HANDLER_FIELD, Modifier.PRIVATE | Modifier.FINAL, DefaultInvocationHandler.class);
		classBuilder.addConstructor(new Class[] { ObjectBuilder.class }, null,
				String.format("%s = new %s($$);", HANDLER_FIELD, DefaultInvocationHandler.class.getName()));

		for (Method method : proxyClass.getMethods()) {
			final int modifiers = method.getModifiers();
			if (Modifier.isPublic(modifiers) && !Modifier.isFinal(modifiers)) {
				final String methodName = method.getName();
				final String methodBody = String.format(
						"{ final %s method = getClass().getMethod(\"%s\", $sig); return ($r) %s.invoke(this, method, $args); }",
						Method.class.getName(), methodName, HANDLER_FIELD);
				classBuilder.addMethod(methodName, Modifier.PUBLIC, method.getReturnType(),
						method.getParameterTypes(), method.getExceptionTypes(), methodBody);
			}
		}

		return classBuilder.build();
	}

	/**
	 * Generates name for proxy class. The name will look like simplified original class name prefixed with <tt>$</tt> and suffixed
	 * with generated unique long number.
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
