/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.hibernate;

import org.hibernate.HibernateException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Ivan Khalopik
 */
public class OrderedEnumUserType<E extends Enum> extends AbstractEnumUserType<E, Integer> {

	public OrderedEnumUserType() {
		super(Types.INTEGER);
	}

	@Override
	public Integer get(ResultSet rs, String name) throws HibernateException, SQLException {
		return rs.getInt(name);
	}

	@Override
	public void set(PreparedStatement st, Integer value, int index) throws HibernateException, SQLException {
		st.setInt(index, value);
	}

	@Override
	protected Integer enumToValue(E e) {
		return e instanceof OrderedEnum ? ((OrderedEnum) e).getOrder() : e.ordinal();
	}
}