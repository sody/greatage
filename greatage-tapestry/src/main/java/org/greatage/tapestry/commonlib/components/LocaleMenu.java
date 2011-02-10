/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.commonlib.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.components.EventLink;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.services.LocalizationSetter;
import org.greatage.tapestry.CSSConstants;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author Ivan Khalopik
 */
@Import(stylesheet = "flags.css")
public class LocaleMenu {

	@Parameter(defaultPrefix = BindingConstants.LITERAL, allowNull = false)
	private String locales;

	private List<String> localeNames;
	private String localeName;

	@Inject
	private Locale currentLocale;

	@Inject
	private LocalizationSetter localizationSetter;

	@Inject
	@Symbol(SymbolConstants.SUPPORTED_LOCALES)
	private String supportedLocales;

	@Component(parameters = "class=prop:localeMenuClass")
	private Menu menu;

	@Component(
			parameters = {
					"selected=currentLocale",
					"name=prop:localeName"})
	private MenuItem menuItem;

	@Component(
			parameters = {
					"event=changeLocale",
					"context=localeName",
					"class=prop:linkClass"})
	private EventLink localeLink;

	protected String defaultLocales() {
		return supportedLocales;
	}

	public String getLocaleMenuClass() {
		return CSSConstants.LOCALE_MENU_CLASS;
	}

	public String getLinkClass() {
		final List<String> classes = CollectionFactory.newList();
		classes.add(CSSConstants.FLAG_CLASS);
		classes.add(localeName);
		return TapestryInternalUtils.toClassAttributeValue(classes);
	}

	public List<String> getLocaleNames() {
		if (localeNames == null) {
			localeNames = Arrays.asList(locales.split(","));
		}
		return localeNames;
	}

	public String getLocaleName() {
		return localeName;
	}

	public void setLocaleName(String localeName) {
		this.localeName = localeName;
	}

	public boolean isCurrentLocale() {
		return String.valueOf(currentLocale).equals(localeName);
	}

	protected void onChangeLocale(String locale) {
		localizationSetter.setLocaleFromLocaleName(locale);
	}
}
