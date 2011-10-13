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

package org.greatage.hibernate.type;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionImplementor;
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
 * This class represents base class for all hibernate custom user types that represent enum as some simple sql type.
 *
 * @param <E> type of enum
 * @param <V> type of corresponding value
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractEnumUserType<E extends Enum, V extends Serializable>
		implements UserType, ParameterizedType {
	private final Map<V, E> constantsByValue = new HashMap<V, E>();
	private final Map<E, V> constantsByEnum = new HashMap<E, V>();

	private Class<E> enumClass;
	private final int sqlType;

	/**
	 * Constructor that creates hibernate custom user type that represent enum as some simple sql type.
	 *
	 * @param sqlType correspondent sql type
	 */
	protected AbstractEnumUserType(final int sqlType) {
		this.sqlType = sqlType;
	}

	/**
	 * Configure hibernate custom type with enum class name stored in <tt>enumClass</tt> parameter.
	 *
	 * @param parameters hibernate custom type configuration parameters
	 */
	@SuppressWarnings("unchecked")
	public void setParameterValues(final Properties parameters) {
		final String enumClassName = parameters.getProperty("enumClass");
		if (enumClassName == null) {
			throw new MappingException("enumClass parameter not specified");
		}

		try {
			final Class<E> configuredEnumClass = (Class<E>) Class.forName(enumClassName);
			setEnumClass(configuredEnumClass);
		} catch (ClassNotFoundException e) {
			throw new MappingException("Enum class " + enumClassName + " not found", e);
		} catch (ClassCastException e) {
			throw new MappingException("Class " + enumClassName + " is not enum class", e);
		}
	}

	/**
	 * Sets enum class and configures all enum-to-value and value-to-enum mappings.
	 *
	 * @param enumClass enum class
	 */
	public void setEnumClass(final Class<E> enumClass) {
		this.enumClass = enumClass;
		constantsByValue.clear();
		for (E e : enumClass.getEnumConstants()) {
			final V value = enumToValue(e);
			constantsByValue.put(value, e);
			constantsByEnum.put(e, value);
		}
	}

	/**
	 * Gets enum class correspondent to this type.
	 *
	 * @return enum class
	 */
	public Class<E> getEnumClass() {
		return enumClass;
	}

	/**
	 * Gets sql type correspondent to this type.
	 *
	 * @return sql type
	 */
	public int getSqlType() {
		return sqlType;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
		final V value = get(rs, names[0]);
		return value == null || rs.wasNull() ? null : getEnum(value);
	}

	/**
	 * {@inheritDoc}
	 */
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, getSqlType());
		} else {
			@SuppressWarnings("unchecked")
			final V v = getValue((E) value);
			set(st, v, index);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<E> returnedClass() {
		return getEnumClass();
	}

	/**
	 * {@inheritDoc}
	 */
	public int[] sqlTypes() {
		return new int[]{sqlType};
	}

	/**
	 * {@inheritDoc}
	 */
	public Object deepCopy(final Object value) {
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object replace(final Object original, final Object target, final Object owner) {
		return original;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isMutable() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public Serializable disassemble(final Object value) {
		return (Enum) value;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object assemble(final Serializable cached, final Object owner) {
		return cached;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(final Object x, final Object y) {
		return x == y;
	}

	/**
	 * {@inheritDoc}
	 */
	public int hashCode(final Object x) {
		return x.hashCode();
	}

	/**
	 * Gets enum constant mapped to specified value.
	 *
	 * @param value enum value
	 * @return enum constant mapped to specified value
	 */
	protected E getEnum(final V value) {
		return constantsByValue.get(value);
	}

	/**
	 * Gets value correspondent to specified enum constant.
	 *
	 * @param enumConstant enum constant
	 * @return value correspondent to specified enum constant
	 */
	protected V getValue(final E enumConstant) {
		return constantsByEnum.get(enumConstant);
	}

	/**
	 * Gets value from result set for specified column name.
	 *
	 * @param rs   SQL result set
	 * @param name SQL column name
	 * @return value from result set for specified column name
	 * @throws SQLException if error occurs when retrieving value
	 */
	protected abstract V get(final ResultSet rs, final String name) throws SQLException;

	/**
	 * Sets value to prepared statement for specified column index.
	 *
	 * @param st	SQL prepered statement
	 * @param index SQL column index
	 * @param value value that will be set to prepared statement
	 * @throws SQLException if error occurs when setting value
	 */
	protected abstract void set(final PreparedStatement st, final V value, final int index) throws SQLException;

	/**
	 * Converts enum constant to value. It is used when initializing hibernate type purpose to generate mappings between
	 * enum constants and values of some simple type.
	 *
	 * @param enumConstant enum constant
	 * @return value correspondent to specified enum constant
	 */
	protected abstract V enumToValue(final E enumConstant);
}
