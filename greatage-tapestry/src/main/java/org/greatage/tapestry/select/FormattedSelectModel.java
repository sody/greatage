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

import org.apache.tapestry5.PropertyConduit;
import org.apache.tapestry5.ioc.MessageFormatter;
import org.apache.tapestry5.ioc.internal.util.MessageFormatterImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * @author Ivan Khalopik
 */
public class FormattedSelectModel<V> extends SimpleSelectModel<V> {
	private final MessageFormatter formatter;
	private final List<PropertyConduit> conduits;

	public FormattedSelectModel(Collection<V> values, String format, List<PropertyConduit> conduits) {
		this(values, new MessageFormatterImpl(format, Locale.getDefault()), conduits);
	}

	public FormattedSelectModel(Collection<V> values, MessageFormatter formatter, List<PropertyConduit> conduits) {
		super(values);
		this.formatter = formatter; //todo: add defence support
		this.conduits = conduits; //todo: add defence support
	}

	@Override
	protected String getLabel(V value) {
		final List<Object> args = new ArrayList<Object>(conduits.size());
		for (PropertyConduit conduit : conduits) {
			args.add(conduit.get(value));
		}
		return formatter.format(args.toArray());
	}
}
