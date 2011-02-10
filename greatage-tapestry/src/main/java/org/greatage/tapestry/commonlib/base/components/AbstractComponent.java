/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.commonlib.base.components;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.ComponentAction;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.PropertyOverrides;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BindingSource;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * @author Ivan Khalopik
 */
public abstract class AbstractComponent {
	@Parameter(value = "this", allowNull = false)
	private PropertyOverrides overrides;

	@Inject
	private Messages messages;

	@Inject
	private ComponentResources resources;

	@Inject
	private JavaScriptSupport javaScriptSupport;

	@Inject
	private BindingSource bindingSource;

	@Inject
	private Environment environment;

	@Environmental(false)
	private FormSupport formSupport;

	public ComponentResources getResources() {
		return resources;
	}

	public PropertyOverrides getOverrides() {
		return overrides;
	}

	public Messages getMessages() {
		return messages;
	}

	public Messages getOverrideMessages() {
		return overrides.getOverrideMessages();
	}

	protected JavaScriptSupport getJavaScriptSupport() {
		return javaScriptSupport;
	}

	protected FormSupport getFormSupport() {
		return formSupport;
	}

	protected <T> void storeAndExecute(T component, ComponentAction<T> action) {
		if (formSupport != null) {
			formSupport.storeAndExecute(component, action);
		} else {
			action.execute(component);
		}
	}

	protected Binding newBinding(final String description, final ComponentResources container, final String defaultPrefix, final String expression) {
		return bindingSource.newBinding(description, container, defaultPrefix, expression);
	}

	protected Binding newBinding(final String description, final String defaultPrefix, final String expression) {
		return bindingSource.newBinding(description, resources, defaultPrefix, expression);
	}

	protected <T> T popFromEnvironment(Class<T> type) {
		return environment.pop(type);
	}

	protected <T> T pushToEnvironment(Class<T> type, T instance) {
		return environment.push(type, instance);
	}

	protected String allocateClientId() {
		return allocateClientId(null);
	}

	protected String allocateClientId(String id) {
		return id != null ?
				getJavaScriptSupport().allocateClientId(id) :
				getJavaScriptSupport().allocateClientId(getResources());
	}
}
