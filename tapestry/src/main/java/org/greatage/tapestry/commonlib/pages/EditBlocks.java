/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.commonlib.pages;

import org.apache.tapestry5.FieldTranslator;
import org.apache.tapestry5.FieldValidator;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.services.PropertyEditContext;
import org.apache.tapestry5.util.EnumSelectModel;
import org.apache.tapestry5.util.EnumValueEncoder;

/**
 * @author Ivan Khalopik
 */
public class EditBlocks {

	@Environmental
	private PropertyEditContext context;

	@Component(
			parameters = {
					"value=context.propertyValue",
					"label=prop:context.label",
					"clientId=prop:context.propertyId",
					"translate=prop:textFieldTranslator",
					"validate=prop:textFieldValidator",
					"annotationProvider=context"})
	private TextField textField;

	@Component(
			parameters = {
					"value=context.propertyValue",
					"label=prop:context.label",
					"clientId=prop:context.propertyId",
					"translate=prop:numberFieldTranslator",
					"validate=prop:numberFieldValidator",
					"annotationProvider=context"})
	private TextField numberField;

	@Component(
			parameters = {
					"value=context.propertyValue",
					"label=prop:context.label",
					"clientId=prop:context.propertyId",
					"translate=prop:passwordFieldTranslator",
					"validate=prop:passwordFieldValidator",
					"annotationProvider=context"})
	private PasswordField passwordField;


	@Component(
			parameters = {
					"value=context.propertyValue",
					"label=prop:context.label",
					"clientId=prop:context.propertyId",
					"translate=prop:textAreaTranslator",
					"validate=prop:textAreaValidator",
					"annotationProvider=context"})
	private TextArea textArea;

	@Component(
			parameters = {
					"value=context.propertyValue",
					"label=prop:context.label",
					"clientId=prop:context.propertyId",
					"validate=prop:dateFieldValidator"})
	private DateField dateField;

	@Component(
			parameters = {
					"value=context.propertyValue",
					"label=prop:context.label",
					"clientId=prop:context.propertyId"})
	private Checkbox checkboxField;

	@Component(
			parameters = {
					"value=context.propertyValue",
					"label=prop:context.label",
					"clientId=prop:context.propertyId",
					"validate=prop:enumSelectValidator",
					"model=enumSelectModel",
					"encoder=enumSelectEncoder"})
	private Select enumSelect;

	public PropertyEditContext getContext() {
		return context;
	}

	public FieldTranslator getTextFieldTranslator() {
		return context.getTranslator(textField);
	}

	public FieldTranslator getNumberFieldTranslator() {
		return context.getTranslator(numberField);
	}

	public FieldTranslator getPasswordFieldTranslator() {
		return context.getTranslator(passwordField);
	}

	public FieldTranslator getTextAreaTranslator() {
		return context.getTranslator(textArea);
	}

	public FieldValidator getTextFieldValidator() {
		return context.getValidator(textField);
	}

	public FieldValidator getNumberFieldValidator() {
		return context.getValidator(numberField);
	}

	public FieldValidator getPasswordFieldValidator() {
		return context.getValidator(passwordField);
	}

	public FieldValidator getTextAreaValidator() {
		return context.getValidator(textArea);
	}

	public FieldValidator getDateFieldValidator() {
		return context.getValidator(dateField);
	}

	public FieldValidator getEnumSelectValidator() {
		return context.getValidator(enumSelect);
	}

	@SuppressWarnings({"unchecked"})
	public ValueEncoder getEnumSelectEncoder() {
		return new EnumValueEncoder(context.getPropertyType());
	}

	@SuppressWarnings({"unchecked"})
	public SelectModel getEnumSelectModel() {
		return new EnumSelectModel(context.getPropertyType(), context.getContainerMessages());
	}
}
