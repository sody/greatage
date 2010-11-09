/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.hibernate.type;

import org.hibernate.HibernateException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Ivan Khalopik
 */
public class NamedEnumUserType<E extends Enum> extends AbstractEnumUserType<E, String> {
	protected NamedEnumUserType() {
		super(Types.VARCHAR);
	}

	@SuppressWarnings({"unchecked"})
	public void nullSafeSet(final PreparedStatement st, final Object value, final int index) throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, getSqlType());
		} else {
			st.setString(index, getValue((E) value));
		}
	}

	@Override
	public String get(final ResultSet rs, final String name) throws HibernateException, SQLException {
		return rs.getString(name);
	}

	@Override
	public void set(final PreparedStatement st, final String value, final int index) throws HibernateException, SQLException {
		st.setString(index, value);
	}

	@Override
	protected String enumToValue(final E e) {
		return e instanceof NamedEnum ? ((NamedEnum) e).getName() : e.name();
	}
}
