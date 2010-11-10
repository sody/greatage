/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.services;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.BeanBlockContribution;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.PersistentFieldStrategy;
import org.greatage.tapestry.CommonSymbols;
import org.greatage.tapestry.PersistenceConstants;
import org.greatage.tapestry.ThemeConstants;
import org.greatage.tapestry.internal.*;

/**
 * @author Ivan Khalopik
 */
public class CommonModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(MenuModelSource.class, MenuModelSourceImpl.class);
		binder.bind(SelectModelBuilder.class);
		binder.bind(RealClassResolver.class);
	}

	public static void contributeFactoryDefaults(MappedConfiguration<String, String> configuration) {
		configuration.add(CommonSymbols.THEME_PATH, "org/greatage/tapestry/theme");
		configuration.add(CommonSymbols.THEME, ThemeConstants.WHITE_THEME);
	}

	public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration) {
		configuration.add(new LibraryMapping("common", "org.greatage.tapestry.commonlib"));
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
}
