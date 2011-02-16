/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.access.ClassAccessSource;
import org.greatage.ioc.access.ClassAccessSourceImpl;
import org.greatage.ioc.annotations.Bind;
import org.greatage.ioc.annotations.Contribute;
import org.greatage.ioc.annotations.Inject;
import org.greatage.ioc.cache.CacheSource;
import org.greatage.ioc.cache.SimpleCacheSource;
import org.greatage.ioc.logging.Log4jLoggerSource;
import org.greatage.ioc.logging.LoggerSource;
import org.greatage.ioc.proxy.JavassistProxyFactory;
import org.greatage.ioc.proxy.ProxyFactory;
import org.greatage.ioc.resource.ClasspathResourceLocator;
import org.greatage.ioc.resource.MessagesSource;
import org.greatage.ioc.resource.MessagesSourceImpl;
import org.greatage.ioc.resource.ResourceLocator;
import org.greatage.ioc.scope.GlobalScope;
import org.greatage.ioc.scope.PrototypeScope;
import org.greatage.ioc.scope.Scope;
import org.greatage.ioc.scope.ScopeConstants;
import org.greatage.ioc.scope.ScopeManager;
import org.greatage.ioc.scope.ScopeManagerImpl;
import org.greatage.ioc.scope.ThreadScope;
import org.greatage.ioc.symbol.DefaultSymbolProvider;
import org.greatage.ioc.symbol.SymbolProvider;
import org.greatage.ioc.symbol.SymbolSource;
import org.greatage.ioc.symbol.SymbolSourceImpl;
import org.greatage.ioc.symbol.SystemSymbolProvider;

/**
 * This class represents base module for Great Age IoC container that configures all needed core services. This are
 * {@link ProxyFactory}, {@link LoggerSource}, {@link ScopeManager}, {@link SymbolSource}, {@link SymbolProvider},
 * {@link ResourceLocator}, {@link MessagesSource}, {@link CacheSource}, {@link ClassAccessSource}.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class IOCModule {

	/**
	 * Binds all needed core services with their default implementations. This are {@link ProxyFactory}, {@link
	 * LoggerSource}, {@link ScopeManager}, {@link SymbolSource}, {@link SymbolProvider}, {@link ResourceLocator}, {@link
	 * MessagesSource}, {@link CacheSource}, {@link ClassAccessSource}.
	 *
	 * @param binder service binder
	 */
	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(ProxyFactory.class, JavassistProxyFactory.class);
		binder.bind(LoggerSource.class, Log4jLoggerSource.class);
		binder.bind(ScopeManager.class, ScopeManagerImpl.class);
		binder.bind(SymbolSource.class, SymbolSourceImpl.class);
		binder.bind(SymbolProvider.class, DefaultSymbolProvider.class).withId("ApplicationSymbolProvider");
		binder.bind(ResourceLocator.class, ClasspathResourceLocator.class);
		binder.bind(MessagesSource.class, MessagesSourceImpl.class);

		binder.bind(ClassAccessSource.class, ClassAccessSourceImpl.class);
	}

	/**
	 * Configures scope manager service to understand all common scopes. This are <tt>global</tt>, <tt>prototype</tt>,
	 * <tt>thread</tt>,
	 *
	 * @param configuration scope manager mapped configuration
	 */
	@Contribute(ScopeManager.class)
	public static void contributeScopeManager(final MappedConfiguration<String, Scope> configuration) {
		configuration.addInstance(ScopeConstants.GLOBAL, GlobalScope.class);
		configuration.addInstance(ScopeConstants.PROTOTYPE, PrototypeScope.class);
		configuration.addInstance(ScopeConstants.THREAD, ThreadScope.class);
	}

	/**
	 * Configures symbol source service with configured application and system symbol providers.
	 *
	 * @param configuration			 symbol source ordered configuration
	 * @param applicationSymbolProvider configured application symbol provider
	 */
	@Contribute(SymbolSource.class)
	public static void contributeSymbolSource(final OrderedConfiguration<SymbolProvider> configuration,
											  @Inject("ApplicationSymbolProvider")
											  final SymbolProvider applicationSymbolProvider) {
		configuration.add(applicationSymbolProvider, "Application");
		configuration.addInstance(SystemSymbolProvider.class, "System", "after:Application");
	}
}
