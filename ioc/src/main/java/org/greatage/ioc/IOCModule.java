/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.annotations.Bind;
import org.greatage.ioc.annotations.Configure;
import org.greatage.ioc.annotations.Inject;
import org.greatage.ioc.logging.Log4jLoggerSource;
import org.greatage.ioc.logging.LoggerSource;
import org.greatage.ioc.proxy.JavaAssistProxyFactory;
import org.greatage.ioc.proxy.ProxyFactory;
import org.greatage.ioc.resource.ClasspathResourceLocator;
import org.greatage.ioc.resource.MessagesSource;
import org.greatage.ioc.resource.MessagesSourceImpl;
import org.greatage.ioc.resource.ResourceLocator;
import org.greatage.ioc.scope.*;
import org.greatage.ioc.symbol.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class IOCModule {

	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(ProxyFactory.class, JavaAssistProxyFactory.class);
		binder.bind(LoggerSource.class, Log4jLoggerSource.class);
		binder.bind(ScopeManager.class, ScopeManagerImpl.class);
		binder.bind(SymbolSource.class, SymbolSourceImpl.class).withId("GASymbolSource");
		binder.bind(SymbolProvider.class, DefaultSymbolProvider.class).withId("ApplicationSymbolProvider");
		binder.bind(ResourceLocator.class, ClasspathResourceLocator.class);
		binder.bind(MessagesSource.class, MessagesSourceImpl.class);
	}

	@Configure(ScopeManager.class)
	public static void configureScopeManager(final MappedConfiguration<String, Scope> configuration) {
		configuration.addInstance(ScopeConstants.GLOBAL, GlobalScope.class);
		configuration.addInstance(ScopeConstants.PROTOTYPE, PrototypeScope.class);
		configuration.addInstance(ScopeConstants.THREAD, ThreadScope.class);
	}

	@Configure(SymbolSource.class)
	public static void configureSymbolSource(final OrderedConfiguration<SymbolProvider> configuration,
											 @Inject("ApplicationSymbolProvider") final SymbolProvider applicationSymbolProvider) {
		configuration.add(applicationSymbolProvider, "Application");
		configuration.addInstance(SystemSymbolProvider.class, "System", "after:Application");
	}

}
