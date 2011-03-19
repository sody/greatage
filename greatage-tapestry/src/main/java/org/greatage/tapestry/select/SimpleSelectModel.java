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

package org.greatage.tapestry.select;

import java.util.Collection;

/**
 * @author Ivan Khalopik
 */
public class SimpleSelectModel<V> extends LazySelectModel<V> {
	private final Collection<V> values;

	public SimpleSelectModel(Collection<V> values) {
		this.values = values; //todo: defense
	}

	@Override
	protected Collection<V> getValues() {
		return values;
	}
}
