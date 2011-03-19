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

import org.greatage.util.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * This class represents default {@link ClassAccess} implementation that uses {@code java.beans} utilities to retrieve
 * all bean properties.
 *
 * @param <T> bean type
 * @author Ivan Khalopik
 * @since 1.1
 */
public class ClassAccessImpl<T> implements ClassAccess<T> {
	private final Map<String, PropertyAccess<T>> properties = CollectionUtils.newMap();
	private final Class<T> type;

	/**
	 * Creates new class access instance for specified class which is configured with its properties using {@code
	 * java.beans} utilities
	 *
	 * @param type bean class
	 * @throws PropertyAccessException when error occurs while resolving bean properties
	 */
	ClassAccessImpl(final Class<T> type) {
		this.type = type;

		try {
			final BeanInfo info = Introspector.getBeanInfo(type);
			final List<PropertyDescriptor> descriptors = CollectionUtils.newList();
			Collections.addAll(descriptors, info.getPropertyDescriptors());

			if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
				final Queue<Class> interfaces = new LinkedList<Class>();
				interfaces.addAll(Arrays.asList(type.getInterfaces()));

				while (!interfaces.isEmpty()) {
					final Class anInterface = interfaces.remove();
					final BeanInfo info1 = Introspector.getBeanInfo(anInterface);

					Collections.addAll(descriptors, info1.getPropertyDescriptors());
					Collections.addAll(interfaces, anInterface.getInterfaces());
				}
			}

			for (PropertyDescriptor descriptor : descriptors) {
				if (descriptor.getPropertyType() != null && !properties.containsKey(descriptor.getName())) {
					final PropertyAccess<T> propertyAccess = new PropertyAccessImpl<T>(this, descriptor);
					properties.put(propertyAccess.getName(), propertyAccess);
				}
			}

			for (Field field : type.getFields()) {
				if (!Modifier.isStatic(field.getModifiers()) && !properties.containsKey(field.getName())) {
					final PropertyAccess<T> propertyAccess = new PropertyAccessImpl<T>(this, field);
					properties.put(propertyAccess.getName(), propertyAccess);
				}
			}
		} catch (Throwable ex) {
			throw new PropertyAccessException(
					String.format("Can not create class access instance for '%s' class", type), ex);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<String> getPropertyNames() {
		return properties.keySet();
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<T> getType() {
		return type;
	}

	/**
	 * {@inheritDoc}
	 */
	public PropertyAccess<T> getPropertyAccess(final String propertyName) {
		assert propertyName != null;

		return properties.get(propertyName);
	}

	/**
	 * {@inheritDoc}
	 */
	public Object get(final T instance, final String propertyName) {
		return propertyAccess(propertyName).get(instance);
	}

	/**
	 * {@inheritDoc}
	 */
	public void set(final T instance, final String propertyName, final Object value) {
		propertyAccess(propertyName).set(instance, value);
	}

	/**
	 * Gets property access instance for specified property, if it is not exists {@link PropertyAccessException} will be
	 * thrown.
	 *
	 * @param propertyName property name
	 * @return property access instance, not null
	 * @throws PropertyAccessException if bean has no such property
	 */
	private PropertyAccess<T> propertyAccess(final String propertyName) {
		if (!properties.containsKey(propertyName)) {
			throw new PropertyAccessException(
					String.format("Class '%s' has no property with name '%s'", type, propertyName));
		}
		return properties.get(propertyName);
	}
}
