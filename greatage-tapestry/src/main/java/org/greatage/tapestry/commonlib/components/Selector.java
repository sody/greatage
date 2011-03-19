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

package org.greatage.tapestry.commonlib.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.apache.tapestry5.services.FormSupport;
import org.greatage.tapestry.CSSConstants;
import org.greatage.tapestry.commonlib.base.components.AbstractComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Import(library = "selector.js")
public class Selector extends AbstractComponent {

	@Parameter
	private Iterable<?> source;

	@Parameter
	private Object value;

	@Parameter(name = "class", defaultPrefix = BindingConstants.LITERAL)
	private String className;

	@Property
	@Parameter
	private Object row;

	@Parameter(required = true)
	private ValueEncoder encoder;

	@Environmental(false)
	private FormSupport formSupport;

	@Inject
	private ComponentDefaultProvider defaultProvider;

	private Map<Object, String> itemIds;
	private Map<Object, String> itemValues;

	private String clientId;
	private String previousId;
	private String nextId;

	ValueEncoder defaultEncoder() {
		return defaultProvider.defaultValueEncoder("value", getResources());
	}

	public String getSelectorClass() {
		final List<String> classes = CollectionFactory.newList();
		classes.add(CSSConstants.CONTAINER_CLASS);
		classes.add(CSSConstants.SELECTOR_CLASS);
		if (className != null) {
			classes.add(className);
		}
		return TapestryInternalUtils.toClassAttributeValue(classes);
	}

	public boolean isVolatile() {
		return formSupport != null;
	}

	public String getClientId() {
		return clientId;
	}

	public String getPreviousId() {
		return previousId;
	}

	public String getPreviousClass() {
		return CSSConstants.SELECTOR_PREVIOUS_CLASS;
	}

	public String getNextId() {
		return nextId;
	}

	public String getNextClass() {
		return CSSConstants.SELECTOR_NEXT_CLASS;
	}

	public String getRowId() {
		return itemIds.get(row);
	}

	public String getRowClass() {
		return CSSConstants.SELECTOR_ITEM_CLASS + (row.equals(value) ? " " : " invisible");
	}

	void setupRender() {
		itemIds = new HashMap<Object, String>();
		itemValues = new HashMap<Object, String>();
		for (Object o : source) {
			final String itemId = allocateClientId("item");
			itemIds.put(o, itemId);
			itemValues.put(o, encoder.toClient(o));
		}
		if (value == null && source.iterator().hasNext()) {
			value = source.iterator().next();
		}

		clientId = allocateClientId();
		previousId = allocateClientId("previous");
		nextId = allocateClientId("next");
	}

	void afterRender() {
		final JSONObject spec = new JSONObject("previousId", previousId, "nextId", nextId, "hiddenId", clientId);
		spec.put("selectedId", itemIds.get(value));
		spec.put("items", generateItems());
		getJavaScriptSupport().addInitializerCall("selector", spec);
	}

	private JSONObject generateItems() {
		final JSONObject result = new JSONObject();
		for (Object item : source) {
			result.put(itemIds.get(item), itemValues.get(item));
		}
		return result;
	}
}
