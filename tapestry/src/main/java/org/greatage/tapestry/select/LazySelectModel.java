/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.select;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

import java.util.Collection;
import java.util.List;

/**
 * @author Ivan Khalopik
 */
public abstract class LazySelectModel<V> extends AbstractSelectModel {
	private List<OptionModel> optionModels;

	public List<OptionGroupModel> getOptionGroups() {
		return null;
	}

	public List<OptionModel> getOptions() {
		if (optionModels == null) {
			optionModels = createOptionModels();
		}
		return optionModels;
	}

	protected abstract Collection<V> getValues();

	protected String getLabel(V value) {
		return String.valueOf(value);
	}

	//todo: add optional values sorting

	protected List<OptionModel> createOptionModels() {
		final List<OptionModel> result = CollectionFactory.newList();
		for (V v : getValues()) {
			result.add(new OptionModelImpl(getLabel(v), v));
		}
		return result;
	}
}
