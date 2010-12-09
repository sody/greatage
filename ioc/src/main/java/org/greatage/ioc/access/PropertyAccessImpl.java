package org.greatage.ioc.access;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class PropertyAccessImpl implements PropertyAccess {
	private final ClassAccess classAccess;
	private final String name;
	private final Class type;

	private final Field field;
	private final Method readMethod;
	private final Method writeMethod;

	public PropertyAccessImpl(final ClassAccess classAccess, final Field field) {
		this.classAccess = classAccess;
		this.name = field.getName();
		this.type = field.getType();

		this.field = field;
		this.readMethod = null;
		this.writeMethod = null;
	}

	public PropertyAccessImpl(final ClassAccess classAccess, final PropertyDescriptor descriptor) {
		this.classAccess = classAccess;
		this.name = descriptor.getName();
		this.type = descriptor.getPropertyType();

		this.field = null;
		this.readMethod = descriptor.getReadMethod();
		this.writeMethod = descriptor.getWriteMethod();
	}

	public ClassAccess getClassAccess() {
		return classAccess;
	}

	public String getName() {
		return name;
	}

	public Class getType() {
		return type;
	}

	public boolean isReadable() {
		return field != null || readMethod != null;
	}

	public boolean isWritable() {
		return field != null || writeMethod != null;
	}

	public Object get(final Object instance) {
		if (!isReadable()) {
			throw new UnsupportedOperationException(String.format("Property '%s' for class '%s' is not readable",
					name, classAccess.getType()));
		}

		try {
			if (field == null) {
				return readMethod.invoke(instance);
			} else {
				return field.get(instance);
			}
		} catch (InvocationTargetException ex) {
			throw new RuntimeException(String.format("Can not read property '%s' of class '%s'",
					name, classAccess.getType()), ex.getTargetException());
		} catch (Exception ex) {
			throw new RuntimeException(String.format("Can not read property '%s' of class '%s'",
					name, classAccess.getType()), ex);
		}
	}

	public void set(final Object instance, final Object value) {
		if (!isWritable()) {
			throw new UnsupportedOperationException(String.format("Property '%s' for class '%s' is not writable",
					name, classAccess.getType()));
		}

		try {
			if (field == null) {
				writeMethod.invoke(instance, value);
			} else {
				field.set(instance, value);
			}
		} catch (InvocationTargetException ex) {
			throw new RuntimeException(String.format("Can not write property '%s' of class '%s'",
					name, classAccess.getType()), ex.getTargetException());
		} catch (Exception ex) {
			throw new RuntimeException(String.format("Can not write property '%s' of class '%s'",
					name, classAccess.getType()), ex);
		}
	}
}
