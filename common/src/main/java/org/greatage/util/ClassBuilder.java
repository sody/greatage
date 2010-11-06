package org.greatage.util;

import javassist.*;

/**
 * This class represents utility that helps to create classes in runtime uses javaassist.
 *
 * @author Ivan Khalopik
 * @param <T> type of class that will be created by {@link #build()} method
 * @since 1.0
 */
public class ClassBuilder<T> {
	private final ClassPool pool;
	private final CtClass ctClass;

	/**
	 * Constructor that creates class builder and initializes it with specified class name. Class instance will be always
	 * created by {@link #build()} method. {@link Object} will be a superclass of created class.
	 *
	 * @param pool ClassPool instance
	 * @param name class name
	 */
	public ClassBuilder(final ClassPool pool, final String name) {
		this(pool, name, false);
	}

	/**
	 * Constructor that creates class builder and initializes it with specified class name and isInterface parameters. If
	 * isInterface parameter is true interface instance will be created by {@link #build()} method. For class creation
	 * {@link Object} will be a superclass of created class.
	 *
	 * @param pool		ClassPool instance
	 * @param name		class name
	 * @param isInterface isInterface parameter
	 */
	public ClassBuilder(final ClassPool pool, final String name, final boolean isInterface) {
		this(pool, name, isInterface, null);
	}

	/**
	 * Constructor that creates class builder and initializes it with specified class name, isInterface and superClass
	 * parameters. If isInterface parameter is true interface instance will be created by {@link #build()} method. For
	 * class creation if superClass is null {@link Object} will be a superclass of created class, if superClass parameter
	 * is interface {@link Object} will be a superclass of created class and specified superClass parameter will be added
	 * to this class like a implemented interface.
	 *
	 * @param pool		ClassPool instance
	 * @param name		class name
	 * @param isInterface isInterface parameter
	 * @param superClass  superClass parameter, may be null
	 */
	public ClassBuilder(final ClassPool pool, final String name, final boolean isInterface, final Class<T> superClass) {
		this.pool = pool;

		if (isInterface) {
			ctClass = superClass == null ?
					pool.makeInterface(name) :
					pool.makeInterface(name, toCtClass(superClass));
		} else {
			ctClass = superClass == null || superClass.isInterface() ?
					pool.makeClass(name) :
					pool.makeClass(name, toCtClass(superClass));
			if (superClass != null && superClass.isInterface()) {
				addInterface(superClass);
			}
		}
	}

	/**
	 * Builds new class instance` represented by this class builder.
	 *
	 * @return new class instance
	 */
	@SuppressWarnings({"unchecked"})
	public Class<T> build() {
		return toClass(ctClass);
	}

	/**
	 * Adds implementation of specified interface to class represented by this class builder.
	 *
	 * @param interfaceClass interface class
	 * @return this class builder instance
	 */
	public ClassBuilder<T> addInterface(final Class interfaceClass) {
		final CtClass ctInterfaceClass = toCtClass(interfaceClass);
		ctClass.addInterface(ctInterfaceClass);
		return this;
	}

	/**
	 * Adds new field declaration with specified name modifiers and type to class represented by this class builder.
	 *
	 * @param name	  field name
	 * @param modifiers field modifiers
	 * @param type	  field class
	 * @return this class builder instance
	 */
	public ClassBuilder<T> addField(final String name, final int modifiers, final Class type) {
		final CtClass ctType = toCtClass(type);
		try {
			final CtField field = new CtField(ctType, name, ctClass);
			field.setModifiers(modifiers);
			ctClass.addField(field);
		} catch (CannotCompileException ex) {
			throw new RuntimeException(String.format("Can't add field '%s' to class '%s'", name, ctClass), ex);
		}
		return this;
	}

	/**
	 * Adds new constructor declaration with specified parameter types, exception declarations and constructor body.
	 *
	 * @param parameterTypes constructor parameter types
	 * @param exceptionTypes constructor exception types
	 * @param body		   constructor body
	 * @return this class builder instance
	 */
	public ClassBuilder<T> addConstructor(final Class[] parameterTypes, final Class[] exceptionTypes, final String body) {
		final CtClass[] ctParameterTypes = toCtClasses(parameterTypes);
		final CtClass[] ctExceptionTypes = toCtClasses(exceptionTypes);

		try {
			final CtConstructor constructor = new CtConstructor(ctParameterTypes, ctClass);
			constructor.setExceptionTypes(ctExceptionTypes);
			constructor.setBody(body);
			ctClass.addConstructor(constructor);
		} catch (Exception ex) {
			throw new RuntimeException(String.format("Can't add constructor to class '%s'", ctClass), ex);
		}
		return this;
	}

	/**
	 * Adds new method declaration with specified name, modifiers, signature and method body.
	 *
	 * @param name		   method name
	 * @param modifiers	  method modifiers
	 * @param returnType	 method return type
	 * @param parameterTypes method parameter types
	 * @param exceptionTypes method exception types
	 * @param body		   method body
	 * @return this class builder instance
	 */
	public ClassBuilder<T> addMethod(final String name, final int modifiers, final Class returnType,
									 final Class[] parameterTypes, final Class[] exceptionTypes, final String body) {
		final CtClass ctReturnType = toCtClass(returnType);
		final CtClass[] ctParameterTypes = toCtClasses(parameterTypes);
		final CtClass[] ctExceptionTypes = toCtClasses(exceptionTypes);

		try {
			final CtMethod method = new CtMethod(ctReturnType, name, ctParameterTypes, ctClass);
			method.setModifiers(modifiers);
			method.setBody(body);
			method.setExceptionTypes(ctExceptionTypes);
			ctClass.addMethod(method);
		} catch (Exception ex) {
			throw new RuntimeException(String.format("Can't add method '%s' to class '%s'", name, ctClass), ex);
		}
		return this;
	}

	/**
	 * Converts original java classes to java assist class representations.
	 *
	 * @param inputClasses original java classes
	 * @return java assist class representations
	 */
	private CtClass[] toCtClasses(final Class[] inputClasses) {
		if (inputClasses == null || inputClasses.length == 0) return null;

		final CtClass[] result = new CtClass[inputClasses.length];
		for (int i = 0; i < inputClasses.length; i++) {
			result[i] = toCtClass(inputClasses[i]);
		}
		return result;
	}

	/**
	 * Converts original java class to java assist class representation.
	 *
	 * @param inputClass original java class
	 * @return java assist class representation
	 */
	private CtClass toCtClass(final Class inputClass) {
		try {
			return pool.getCtClass(toClassName(inputClass));
		} catch (NotFoundException ex) {
			throw new RuntimeException(String.format("Can't convert class '%s'", inputClass), ex);
		}
	}

	/**
	 * Converts java assist class to original java class.
	 *
	 * @param ctClass java assist class
	 * @return original java class
	 */
	private Class toClass(final CtClass ctClass) {
		try {
			return pool.toClass(ctClass);
		} catch (CannotCompileException ex) {
			throw new RuntimeException(String.format("Can't convert class '%s'", ctClass), ex);
		}
	}

	/**
	 * Gets string representation of class name. It differs from original class name only for arrays.
	 *
	 * @param inputClass class
	 * @return string representation of class name
	 */
	private String toClassName(final Class inputClass) {
		if (inputClass.isArray())
			return toClassName(inputClass.getComponentType()) + "[]";

		return inputClass.getName();
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("name", ctClass.getName());
		return builder.toString();
	}
}
