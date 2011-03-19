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

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.beaneditor.PropertyModel;
import org.apache.tapestry5.corelib.base.BaseMessages;
import org.apache.tapestry5.internal.beaneditor.PropertyModelImpl;
import org.apache.tapestry5.internal.services.CoercingPropertyConduitWrapper;
import org.apache.tapestry5.internal.util.StringRenderable;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Primary;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.PropertyAdapter;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.*;
import org.greatage.tapestry.commonlib.base.components.AbstractComponent;

import java.lang.annotation.Annotation;
import java.util.Locale;

/**
 * @author Ivan Khalopik
 */
public class BeanProperty extends AbstractComponent implements PropertyOutputContext, PropertyEditContext, Field {

	@Parameter(required = true)
	private Object object;

	@Parameter
	private boolean editable;

	@Parameter
	private PropertyModel model;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String property;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String dataType;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String validate;

	@Parameter(defaultPrefix = BindingConstants.TRANSLATE)
	private FieldTranslator<Object> translate;

	@Inject
	private Locale locale;

	@Inject
	private PropertyConduitSource propertyConduitSource;

	@Inject
	private TypeCoercer typeCoercer;

	@Inject
	private PropertyAccess propertyAccess;

	@Primary
	@Inject
	private DataTypeAnalyzer dataTypeAnalyzer;

	@Inject
	private BeanBlockSource beanBlockSource;

	@Inject
	private FieldTranslatorSource fieldTranslatorSource;

	@Inject
	private FieldValidatorDefaultSource fieldValidatorDefaultSource;

	@Inject
	private FieldValidatorSource fieldValidatorSource;

	public Object getPropertyValue() {
		return model.getConduit() != null ? model.getConduit().get(object) : null;
	}

	public void setPropertyValue(final Object value) {
		if (model.getConduit() != null) {
			model.getConduit().set(object, value);
		}
	}

	public String getControlName() {
		return null;  //todo: default method body
	}

	public String getLabel() {
		return model.getLabel();
	}

	public boolean isDisabled() {
		return false;  //todo: default method body
	}

	public boolean isRequired() {
		return getValidator(this).isRequired();//todo: cache
	}

	public String getPropertyId() {
		return model.getId();
	}

	public Class getPropertyType() {
		return model.getPropertyType();
	}

	public Messages getContainerMessages() {
		return getOverrideMessages();
	}

	public String getPropertyName() {
		return model.getPropertyName();
	}

	public FieldTranslator getTranslator(Field field) {
		return translate != null ? translate : fieldTranslatorSource.createDefaultTranslator(
				field, getPropertyName(), getOverrideMessages(), locale, getPropertyType(), this);
	}

	public FieldValidator getValidator(Field field) {
		return validate != null ? fieldValidatorSource.createValidators(field, validate) :
				fieldValidatorDefaultSource.createDefaultValidator(
						field, getPropertyName(), getOverrideMessages(), locale, getPropertyType(), this);
	}

	public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
		return model.getAnnotation(annotationClass);
	}

	protected void setupRender() {
		storeAndExecute(this, new SetupContext());
	}

	protected void cleanupRender() {
		storeAndExecute(this, new CleanupContext());
	}

	protected Object beginRender() {
		final String dataType = model.getDataType();
		if (dataType == null) {
			throw new RuntimeException(String.format("The data type for property '%s' of %s is null.", getPropertyName(), object));
		}
		if (editable) {
			return beanBlockSource.getEditBlock(dataType);
		} else if (beanBlockSource.hasDisplayBlock(dataType)) {
			return beanBlockSource.getDisplayBlock(dataType);
		}
		return readPropertyForObject();
	}

	protected boolean beforeRenderTemplate() {
		return false;
	}

	protected Renderable readPropertyForObject() {
		try {
			final Object value = getPropertyValue();
			return new StringRenderable(value != null ? value.toString() : null);
		}
		catch (NullPointerException ex) {
			throw new NullPointerException(BaseMessages.nullValueInPath(getPropertyName()));
		}
	}

	protected void setupContext() {
		if (model == null) {
			//todo: defence
//			Defense.notNull(property, "property");
			final Class propertyType = getResources().getBoundType("object");
			final PropertyConduit propertyConduit = new CoercingPropertyConduitWrapper(
					propertyConduitSource.create(propertyType, property), typeCoercer);
			model = new PropertyModelImpl(null, property, propertyConduit, getOverrideMessages());
			if (dataType == null) {
				final PropertyAdapter adapter = propertyAccess.getAdapter(propertyType).getPropertyAdapter(property);
				model.dataType(dataTypeAnalyzer.identifyDataType(adapter));
			} else {
				model.dataType(dataType);
			}
		}

		if (editable) {
			pushToEnvironment(PropertyEditContext.class, this);
		} else {
			pushToEnvironment(PropertyOutputContext.class, this);
		}
	}

	protected void cleanupContext() {
		if (editable) {
			popFromEnvironment(PropertyEditContext.class);
		} else {
			popFromEnvironment(PropertyOutputContext.class);
		}
	}

	public String getClientId() {
		return getPropertyId();
	}

	static class SetupContext implements ComponentAction<BeanProperty> {
		public void execute(BeanProperty component) {
			component.setupContext();
		}
	}

	static class CleanupContext implements ComponentAction<BeanProperty> {
		public void execute(BeanProperty component) {
			component.cleanupContext();
		}
	}
}
