/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
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
