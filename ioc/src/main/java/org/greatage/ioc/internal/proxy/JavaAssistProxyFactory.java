package org.greatage.ioc.internal.proxy;

import org.greatage.ioc.services.ObjectBuilder;
import org.greatage.util.ClassBuilder;
import org.greatage.util.DescriptionBuilder;
import org.greatage.util.ReflectionUtils;
import javassist.ClassPool;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.atomic.AtomicLong;


/**
 * This class represents proxy factory implementation using javaassist library.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JavaAssistProxyFactory extends AbstractProxyFactory {
	private static final AtomicLong UID_GENERATOR = new AtomicLong(System.currentTimeMillis());

	private static final String BUILDER_FIELD = "_builder";
	private static final String DELEGATE_FIELD = "_delegate";
	private static final String DELEGATE_METHOD = "_getDelegate";

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

	public <T> T createProxy(final ObjectBuilder<T> builder) {
		validate(builder);

		final Class<T> proxyClass = createProxyClass(builder);
		return ReflectionUtils.newInstance(proxyClass, builder);
	}

	/**
	 * Generates proxy class around specified object builder.
	 *
	 * @param builder object builder
	 * @param <T>     type of proxy class
	 * @return proxy class around specified object builder
	 */
	private <T> Class<T> createProxyClass(final ObjectBuilder<T> builder) {
		final Class<T> proxyClass = builder.getObjectClass();
		final String className = generateName(proxyClass);

		final ClassBuilder<T> classBuilder = new ClassBuilder<T>(pool, className, false, proxyClass);

		classBuilder.addField(BUILDER_FIELD, Modifier.PRIVATE | Modifier.FINAL, ObjectBuilder.class);
		classBuilder.addField(DELEGATE_FIELD, Modifier.PRIVATE, proxyClass);

		classBuilder.addConstructor(new Class[]{ObjectBuilder.class}, null, String.format("%s = $1;", BUILDER_FIELD));

		final String delegateBody = String.format("{ if (%s == null) { %s = (%s) %s.build(); } return %s; }",
				DELEGATE_FIELD, DELEGATE_FIELD, proxyClass.getName(), BUILDER_FIELD, DELEGATE_FIELD);
		classBuilder.addMethod(DELEGATE_METHOD, Modifier.PRIVATE, proxyClass, null, null, delegateBody);

		for (Method method : proxyClass.getMethods()) {
			final int modifiers = method.getModifiers();
			if (Modifier.isPublic(modifiers) && !Modifier.isFinal(modifiers)) {
				final String methodName = method.getName();
				final String methodBody = String.format("return ($r) %s().%s($$);", DELEGATE_METHOD, methodName);
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
