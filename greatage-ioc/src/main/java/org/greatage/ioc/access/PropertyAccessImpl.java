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

package org.greatage.ioc.access;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class represents default {@link PropertyAccess} implementation that uses field or read-write method to access
 * bean property value.
 *
 * @param <T> bean type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class PropertyAccessImpl<T> implements PropertyAccess<T> {
	private final ClassAccess<T> classAccess;
	private final String name;
	private final Class type;

	private final Field field;
	private final Method readMethod;
	private final Method writeMethod;

	/**
	 * Creates new instance of property access with defined property field.
	 *
	 * @param classAccess class access instance
	 * @param field	   property field
	 */
	PropertyAccessImpl(final ClassAccess<T> classAccess, final Field field) {
		this.classAccess = classAccess;
		this.name = field.getName();
		this.type = field.getType();

		this.field = field;
		this.readMethod = null;
		this.writeMethod = null;
	}

	/**
	 * Creates new instance of property access with defined property descriptor.
	 *
	 * @param classAccess class access instance
	 * @param descriptor  property descriptor
	 */
	PropertyAccessImpl(final ClassAccess<T> classAccess, final PropertyDescriptor descriptor) {
		this.classAccess = classAccess;
		this.name = descriptor.getName();
		this.type = descriptor.getPropertyType();

		this.field = null;
		this.readMethod = descriptor.getReadMethod();
		this.writeMethod = descriptor.getWriteMethod();
	}

	/**
	 * {@inheritDoc}
	 */
	public ClassAccess<T> getClassAccess() {
		return classAccess;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	public Class getType() {
		return type;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isReadable() {
		return field != null || readMethod != null;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isWritable() {
		return field != null || writeMethod != null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object get(final T instance) {
		if (!isReadable()) {
			throw new PropertyAccessException(
					String.format("Property '%s' for class '%s' is not readable", name, classAccess.getType()));
		}

		try {
			if (field == null) {
				return readMethod.invoke(instance);
			} else {
				return field.get(instance);
			}
		} catch (InvocationTargetException ex) {
			throw new PropertyAccessException(String.format("Can not read property '%s' of class '%s'",
					name, classAccess.getType()), ex.getTargetException());
		} catch (Exception ex) {
			throw new PropertyAccessException(String.format("Can not read property '%s' of class '%s'",
					name, classAccess.getType()), ex);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void set(final T instance, final Object value) {
		if (!isWritable()) {
			throw new PropertyAccessException(
					String.format("Property '%s' for class '%s' is not writable", name, classAccess.getType()));
		}

		try {
			if (field == null) {
				writeMethod.invoke(instance, value);
			} else {
				field.set(instance, value);
			}
		} catch (InvocationTargetException ex) {
			throw new PropertyAccessException(String.format("Can not write property '%s' of class '%s'",
					name, classAccess.getType()), ex.getTargetException());
		} catch (Exception ex) {
			throw new PropertyAccessException(String.format("Can not write property '%s' of class '%s'",
					name, classAccess.getType()), ex);
		}
	}
}
