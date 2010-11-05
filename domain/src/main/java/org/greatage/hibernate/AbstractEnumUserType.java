package org.greatage.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Ivan Khalopik
 */
public abstract class AbstractEnumUserType<E extends Enum, V extends Serializable> implements UserType, ParameterizedType {
	private final Map<V, E> constantsByValue = new HashMap<V, E>();
	private final Map<E, V> constantsByEnum = new HashMap<E, V>();

	private Class<E> enumClass;
	private final int sqlType;

	protected AbstractEnumUserType(int sqlType) {
		this.sqlType = sqlType;
	}

	@SuppressWarnings({"unchecked"})
	public void setParameterValues(Properties parameters) {
		final String enumClassName = parameters.getProperty("enumClass");
		if (enumClassName == null) {
			throw new MappingException("enumClassName parameter not specified");
		}

		try {
			Class<E> enumClass = (Class<E>) Class.forName(enumClassName);
			setEnumClass(enumClass);
		} catch (ClassNotFoundException e) {
			throw new MappingException("enumClass " + enumClassName + " not found", e);
		} catch (ClassCastException e) {
			throw new MappingException("enumClass " + enumClassName + " not enum class", e);
		}
	}

	public void setEnumClass(Class<E> enumClass) {
		this.enumClass = enumClass;
		constantsByValue.clear();
		for (E e : enumClass.getEnumConstants()) {
			final V value = enumToValue(e);
			constantsByValue.put(value, e);
			constantsByEnum.put(e, value);
		}
	}

	public Class<E> getEnumClass() {
		return enumClass;
	}

	public int getSqlType() {
		return sqlType;
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		final V value = get(rs, names[0]);
		return value == null || rs.wasNull() ? null : getEnum(value);
	}

	@SuppressWarnings({"unchecked"})
	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, getSqlType());
		} else {
			final V v = getValue((E) value);
			set(st, v, index);
		}
	}

	public Class<E> returnedClass() {
		return getEnumClass();
	}

	public int[] sqlTypes() {
		return new int[]{sqlType};
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

	public boolean isMutable() {
		return false;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		return (Enum) value;
	}

	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		return x == y;
	}

	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	protected E getEnum(V order) {
		return constantsByValue.get(order);
	}

	protected V getValue(E e) {
		return constantsByEnum.get(e);
	}

	public abstract V get(ResultSet rs, String name) throws HibernateException, SQLException;

	public abstract void set(PreparedStatement st, V value, int index) throws HibernateException, SQLException;

	protected abstract V enumToValue(E e);
}
