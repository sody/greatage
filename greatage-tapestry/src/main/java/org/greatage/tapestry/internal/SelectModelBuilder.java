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

package org.greatage.tapestry.internal;

import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.PropertyConduit;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.internal.SelectModelImpl;
import org.apache.tapestry5.ioc.MessageFormatter;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.services.PropertyConduitSource;
import org.greatage.tapestry.select.FormattedSelectModel;
import org.greatage.tapestry.select.SimpleSelectModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Ivan Khalopik
 */
public class SelectModelBuilder {
	private static final SelectModel EMPTY_MODEL = new SelectModelImpl(null, CollectionFactory.<OptionModel>newList());

	private final PropertyConduitSource conduitSource;

	public SelectModelBuilder(PropertyConduitSource conduitSource) {
		this.conduitSource = conduitSource;
	}

	public SelectModel buildEmpty() {
		return EMPTY_MODEL;
	}

	public <V> SelectModel buildSimple(Collection<V> values) {
		return new SimpleSelectModel<V>(values);
	}

	public <V> SelectModel buildFormatted(Class<V> valueClass, Collection<V> values, String format, String... parameters) {
		return new FormattedSelectModel<V>(values, format, getConduits(valueClass, parameters));
	}

	public <V> SelectModel buildFormatted(Class<V> valueClass, Collection<V> values, MessageFormatter formatter, String... parameters) {
		return new FormattedSelectModel<V>(values, formatter, getConduits(valueClass, parameters));
	}

	private <V> List<PropertyConduit> getConduits(Class<V> valueClass, String... parameters) {
		final List<PropertyConduit> conduits = new ArrayList<PropertyConduit>();
		for (String parameter : parameters) {
			final PropertyConduit conduit = conduitSource.create(valueClass, parameter);
			conduits.add(conduit);
		}
		return conduits;
	}
}
