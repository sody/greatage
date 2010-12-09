package org.greatage.ioc.access;

import org.greatage.util.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class ClassAccessImpl implements ClassAccess {
	private final Map<String, PropertyAccess> properties = CollectionUtils.newMap();
	private final Class type;

	public ClassAccessImpl(final Class type) {
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
					final PropertyAccess propertyAccess = new PropertyAccessImpl(this, descriptor);
					properties.put(propertyAccess.getName(), propertyAccess);
				}
			}

			for (Field field : type.getFields()) {
				if (!Modifier.isStatic(field.getModifiers()) && !properties.containsKey(field.getName())) {
					final PropertyAccess propertyAccess = new PropertyAccessImpl(this, field);
					properties.put(propertyAccess.getName(), propertyAccess);
				}
			}
		} catch (Throwable ex) {
			throw new RuntimeException(ex);
		}
	}

	public Collection<String> getPropertyNames() {
		return properties.keySet();
	}

	public Class getType() {
		return type;
	}

	public PropertyAccess getPropertyAccess(final String propertyName) {
		return properties.get(propertyName);
	}

	public Object get(final Object instance, final String propertyName) {
		return propertyAccess(propertyName).get(instance);
	}

	public void set(final Object instance, final String propertyName, final Object value) {
		propertyAccess(propertyName).set(instance, value);
	}

	private PropertyAccess propertyAccess(final String propertyName) {
		final PropertyAccess access = getPropertyAccess(propertyName);
		if (access == null) {
			throw new IllegalArgumentException(String.format("Class '%s' has no property with name '%s'",
					type, propertyName));
		}
		return properties.get(propertyName);
	}
}
