/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.services;

import org.apache.tapestry5.internal.services.GenericValueEncoderFactory;
import org.apache.tapestry5.internal.services.ResourceCache;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.BeanBlockContribution;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.PersistentFieldStrategy;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.greatage.tapestry.CommonSymbols;
import org.greatage.tapestry.PersistenceConstants;
import org.greatage.tapestry.ThemeConstants;
import org.greatage.tapestry.internal.EncodedFlashPersistentFieldStrategy;
import org.greatage.tapestry.internal.EncodedSessionPersistentFieldStrategy;
import org.greatage.tapestry.internal.LocaleValueEncoder;
import org.greatage.tapestry.internal.MenuModelSourceImpl;
import org.greatage.tapestry.internal.RealClassResolver;
import org.greatage.tapestry.internal.SelectModelBuilder;
import org.greatage.tapestry.internal.SvgResourceCache;
import org.greatage.tapestry.internal.TimeZoneValueEncoder;

import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Ivan Khalopik
 */
public class CommonModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(MenuModelSource.class, MenuModelSourceImpl.class);
		binder.bind(SelectModelBuilder.class, SelectModelBuilder.class);
		binder.bind(RealClassResolver.class, RealClassResolver.class);
	}

	public static void contributeFactoryDefaults(MappedConfiguration<String, String> configuration) {
		configuration.add(CommonSymbols.THEME_PATH, "org/greatage/tapestry/theme");
		configuration.add(CommonSymbols.THEME, ThemeConstants.WHITE_THEME);
	}

	public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration) {
		configuration.add(new LibraryMapping("common", "org.greatage.tapestry.commonlib"));
	}

	public void contributeValueEncoderSource(final MappedConfiguration<Class, ValueEncoderFactory> configuration) {
		configuration.add(Locale.class, GenericValueEncoderFactory.create(new LocaleValueEncoder()));
		configuration.add(TimeZone.class, GenericValueEncoderFactory.create(new TimeZoneValueEncoder()));
	}

	public static void contributeClasspathAssetAliasManager(MappedConfiguration<String, String> configuration,
															@Symbol(CommonSymbols.THEME_PATH) String themePath) {
		configuration.add("theme", themePath);
	}

	public static void contributePersistentFieldManager(MappedConfiguration<String, PersistentFieldStrategy> configuration) {
		configuration.addInstance(PersistenceConstants.ENCODED_SESSION, EncodedSessionPersistentFieldStrategy.class);
		configuration.addInstance(PersistenceConstants.ENCODED_FLASH, EncodedFlashPersistentFieldStrategy.class);
	}

	public void contributeBeanBlockOverrideSource(Configuration<BeanBlockContribution> configuration) {
		addEditBlock(configuration, "text");
		addEditBlock(configuration, "number");
		addEditBlock(configuration, "enum");
		addEditBlock(configuration, "boolean");
		addEditBlock(configuration, "date");
		addEditBlock(configuration, "password");
		addEditBlock(configuration, "longtext");
	}

	private void addEditBlock(Configuration<BeanBlockContribution> configuration, String dataType) {
		addEditBlock(configuration, dataType, dataType);
	}

	private void addEditBlock(Configuration<BeanBlockContribution> configuration, String dataType, String blockId) {
		configuration.add(new BeanBlockContribution(dataType, "common/EditBlocks", blockId, true));
	}

	public ResourceCache decorateResourceCache(final ResourceCache resourceCache) {
		return new SvgResourceCache(resourceCache);
	}
}
