/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.internal.proxy;

import javassist.ClassPool;
import org.greatage.ioc.services.MethodAdvice;
import org.greatage.ioc.services.ObjectBuilder;
import org.greatage.util.ClassBuilder;
import org.greatage.util.DescriptionBuilder;
import org.greatage.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


/**
 * This class represents proxy factory implementation using javaassist library.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JavaAssistProxyFactory extends AbstractProxyFactory {
	private static final AtomicLong UID_GENERATOR = new AtomicLong(System.currentTimeMillis());

	private static final String HANDLER_FIELD = "_handler";

	private final ClassPool pool;

	/**
	 * Creates new instance of java assist proxy factory with default class pool.
	 */
	public JavaAssistProxyFactory() {
		this(ClassPool.getDefault());
	}

	/**
	 * Creates new instance of java assist proxy factory with specified class pool.
	 *
	 * @param pool class pool
	 */
	public JavaAssistProxyFactory(final ClassPool pool) {
		this.pool = pool;
	}

	public <T> T createProxy(final ObjectBuilder<T> builder, final List<MethodAdvice> advices) {
		validate(builder);

		final Class<T> proxyClass = createProxyClass(builder);
		return ReflectionUtils.newInstance(proxyClass, builder, advices);
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

		classBuilder.addField(HANDLER_FIELD, Modifier.PRIVATE | Modifier.FINAL, JavaAssistInvocationHandler.class);
		classBuilder.addConstructor(new Class[]{ObjectBuilder.class, List.class}, null, String.format("%s = new %s($$);", HANDLER_FIELD, JavaAssistInvocationHandler.class.getName()));

		for (Method method : proxyClass.getMethods()) {
			final int modifiers = method.getModifiers();
			if (Modifier.isPublic(modifiers) && !Modifier.isFinal(modifiers)) {
				final String methodName = method.getName();
				final StringBuilder parameterTypeNames = new StringBuilder();
				for (Class<?> parameterType : method.getParameterTypes()) {
					if (parameterTypeNames.length() > 0) {
						parameterTypeNames.append(",");
					}
					parameterTypeNames.append(parameterType.getName()).append(".class");
				}
				final String parameterTypes = parameterTypeNames.length() > 0 ?
						"new Class[]{" + parameterTypeNames.toString() + "}" :
						"null";
				final String parameters = parameterTypeNames.length() > 0 ?
						"new Object[]{ $$ }" :
						"null";

				final String methodBody = String.format("{ %s realMethod = %s.getDelegate().getClass().getMethod(\"%s\", $sig); return ($r) %s.invoke(realMethod, $args); }",
						Method.class.getName(), HANDLER_FIELD, methodName, HANDLER_FIELD);
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
	private String generateName(Class inputClass) {
		final String uid = Long.toHexString(UID_GENERATOR.getAndIncrement());
		return "$" + inputClass.getSimpleName() + "_" + uid;
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("pool", pool);
		return builder.toString();
	}
}
