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

package org.greatage.domain.jdo;

import org.greatage.domain.sql.SqlProperty;
import org.greatage.util.DescriptionBuilder;

import javax.jdo.Query;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JdoProperty extends SqlProperty {
	private final String property;
	private final String fullProperty;
	private final Query query;

	public JdoProperty(final Query query, final String path, final String property) {
		this.query = query;
		this.property = property;
		this.fullProperty = path != null ? path + "." + property : property;
	}

	public String getProperty() {
		return property;
	}

	protected String property() {
		return fullProperty;
	}

	public void sort() {
		sort(true, true);
	}

	public void sort(final boolean ascending) {
		sort(ascending, true);
	}

	public void sort(final boolean ascending, final boolean ignoreCase) {
		query.setOrdering(property());
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append(property);
		return builder.toString();
	}
}
