/*
 * Copyright 2011 Ivan Khalopik
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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * This class represent custom hibernate type that maps enum constants as strings. This string representation will be
 * taken from {@link org.greatage.hibernate.type.NamedEnum#getName()} method if enum implements this interface or from
 * standard enum method {@link Enum#name()}.
 *
 * @param <E> type of enum
 * @author Ivan Khalopik
 * @since 1.0
 */
public class NamedEnumUserType<E extends Enum> extends AbstractEnumUserType<E, String> {

	/**
	 * Creates new instance of custom hibernate type that maps enum constants as strings.
	 */
	public NamedEnumUserType() {
		super(Types.VARCHAR);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String get(final ResultSet rs, final String name) throws SQLException {
		return rs.getString(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void set(final PreparedStatement st, final String value, final int index) throws SQLException {
		st.setString(index, value);
	}

	/**
	 * Converts enum constant to correspondent string value. This string representation will be taken from {@link
	 * org.greatage.hibernate.type.NamedEnum#getName()} method if enum implements this interface or from standard enum
	 * method {@link Enum#name()}.
	 *
	 * @param enumConstant enum constant
	 * @return unique string representation of enum constant
	 */
	@Override
	protected String enumToValue(final E enumConstant) {
		return enumConstant instanceof NamedEnum ? ((NamedEnum) enumConstant).getName() : enumConstant.name();
	}
}
