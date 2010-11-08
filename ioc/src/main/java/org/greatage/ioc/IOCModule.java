/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.annotations.Bind;
import org.greatage.ioc.annotations.Configure;
import org.greatage.ioc.internal.logging.Log4jLoggerSource;
import org.greatage.ioc.internal.resource.MessagesSourceImpl;
import org.greatage.ioc.internal.proxy.JavaAssistProxyFactory;
import org.greatage.ioc.internal.scope.GlobalScope;
import org.greatage.ioc.internal.scope.PrototypeScope;
import org.greatage.ioc.internal.scope.ScopeManagerImpl;
import org.greatage.ioc.internal.scope.ThreadScope;
import org.greatage.ioc.internal.symbol.DefaultSymbolProvider;
import org.greatage.ioc.internal.symbol.SymbolSourceImpl;
import org.greatage.ioc.internal.symbol.SystemSymbolProvider;
import org.greatage.ioc.services.*;
import org.greatage.util.Ordered;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class IOCModule {

	@Bind
	public static void bind(ServiceBinder binder) {
		binder.bind(ProxyFactory.class, JavaAssistProxyFactory.class).withScope(ScopeConstants.INTERNAL).lazy(false);
		binder.bind(LoggerSource.class, Log4jLoggerSource.class).withScope(ScopeConstants.INTERNAL);
		binder.bind(ScopeManager.class, ScopeManagerImpl.class).withScope(ScopeConstants.INTERNAL);
		binder.bind(SymbolSource.class, SymbolSourceImpl.class).withScope(ScopeConstants.INTERNAL);
		binder.bind(SymbolProvider.class, DefaultSymbolProvider.class).withScope(ScopeConstants.INTERNAL);
		binder.bind(MessagesSource.class, MessagesSourceImpl.class).withScope(ScopeConstants.GLOBAL);
	}


	@Configure(ScopeManager.class)
	public static void configureScopeManager(final MappedConfiguration<String, Scope> configuration) {
		configuration.addInstance(ScopeConstants.GLOBAL, GlobalScope.class);
		configuration.addInstance(ScopeConstants.PROTOTYPE, PrototypeScope.class);
		configuration.addInstance(ScopeConstants.THREAD, ThreadScope.class);
	}

	@Configure(SymbolProvider.class)
	public static void configureSymbolProvider(final MappedConfiguration<String, String> configuration) {
		configuration.add(IOCSymbols.DEFAULT_SCOPE, ScopeConstants.GLOBAL);
	}

	@Configure(SymbolSource.class)
	public static void configureSymbolSource(final OrderedConfiguration<SymbolProvider> configuration, final SymbolProvider defaultProvider) {
		configuration.add(defaultProvider, DefaultSymbolProvider.PROVIDER_ID);
		configuration.addInstance(SystemSymbolProvider.class, SystemSymbolProvider.PROVIDER_ID,
				Ordered.AFTER + DefaultSymbolProvider.PROVIDER_ID);
	}

}
