package org.greatage.hibernate;

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
	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, getSqlType());
		} else {
			st.setString(index, getValue((E) value));
		}
	}

	@Override
	public String get(ResultSet rs, String name) throws HibernateException, SQLException {
		return rs.getString(name);
	}

	@Override
	public void set(PreparedStatement st, String value, int index) throws HibernateException, SQLException {
		st.setString(index, value);
	}

	@Override
	protected String enumToValue(E e) {
		return e instanceof NamedEnum ? ((NamedEnum) e).getName() : e.name();
	}
}
