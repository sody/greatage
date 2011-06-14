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

package org.greatage.ioc;

import org.greatage.ioc.access.ClassAccessSource;
import org.greatage.ioc.access.ClassAccessSourceImpl;
import org.greatage.ioc.annotations.Bind;
import org.greatage.ioc.annotations.Contribute;
import org.greatage.ioc.coerce.*;
import org.greatage.ioc.inject.*;
import org.greatage.ioc.logging.LoggerSource;
import org.greatage.ioc.logging.Slf4jLoggerSource;
import org.greatage.ioc.proxy.JavassistProxyFactory;
import org.greatage.ioc.proxy.ProxyFactory;
import org.greatage.ioc.resource.*;
import org.greatage.ioc.scope.*;
import org.greatage.ioc.symbol.*;

/**
 * This class represents base module for Great Age IoC container that configures all needed core services. This are {@link
 * ProxyFactory}, {@link LoggerSource}, {@link ScopeManager}, {@link SymbolSource}, {@link SymbolProvider}, {@link
 * ResourceLocator}, {@link MessagesSource}, {@link ClassAccessSource}.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class IOCModule {

	/**
	 * Binds all needed core services with their default implementations. This are {@link ProxyFactory}, {@link LoggerSource}, {@link
	 * ScopeManager}, {@link SymbolSource}, {@link SymbolProvider}, {@link ResourceLocator}, {@link MessagesSource}, {@link
	 * ClassAccessSource}.
	 *
	 * @param binder service binder
	 */
	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(ProxyFactory.class, JavassistProxyFactory.class).eager();
		binder.bind(ScopeManager.class, ScopeManagerImpl.class).eager();
		binder.bind(LoggerSource.class, Slf4jLoggerSource.class).eager();

		binder.bind(ServiceLocator.class, ServiceLocatorImpl.class);
		binder.bind(Injector.class, DefaultInjector.class);
		binder.bind(TypeCoercer.class, TypeCoercerImpl.class);
		binder.bind(CoercionProvider.class, DefaultCoercionProvider.class);
		binder.bind(SymbolSource.class, SymbolSourceImpl.class);
		binder.bind(SymbolProvider.class, DefaultSymbolProvider.class);

		binder.bind(ResourceLocator.class, ResourceLocatorImpl.class);
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
	public static void contributeScopeManager(final Configuration<Scope> configuration, final Scope defaultScope) {
		configuration.add(defaultScope);
		configuration.addInstance(PrototypeScope.class);
		configuration.addInstance(ThreadScope.class);
	}

	@Contribute(Injector.class)
	public static void contributeInjector(final OrderedConfiguration<InjectionProvider> configuration) {
		configuration.addInstance(DefaultInjectionProvider.class, "Default");
		configuration.addInstance(SymbolInjectionProvider.class, "Symbol", "before:Default");
		configuration.addInstance(LoggerInjectionProvider.class, "Logger", "before:Symbol");
	}

	/**
	 * Configures symbol source service with configured application and system symbol providers.
	 *
	 * @param configuration  symbol source ordered configuration
	 * @param symbolProvider configured application symbol provider
	 */
	@Contribute(SymbolSource.class)
	public static void contributeSymbolSource(final OrderedConfiguration<SymbolProvider> configuration,
											  final SymbolProvider symbolProvider) {
		configuration.add(symbolProvider, "Application");
		configuration.addInstance(SystemSymbolProvider.class, "System", "after:Application");
	}

	/**
	 * Configures type coercer service with configured default coercion provider and string-to-enum provider.
	 *
	 * @param configuration	type coercer configuration
	 * @param coercionProvider default coercion provider service
	 */
	@Contribute(TypeCoercer.class)
	public static void contributeTypeCoercer(final Configuration<CoercionProvider> configuration,
											 final CoercionProvider coercionProvider) {
		configuration.add(coercionProvider);
		configuration.addInstance(StringToEnumCoercionProvider.class);
	}

	/**
	 * Configures default coercion provider with all basic coercions.
	 *
	 * @param configuration default coercion provider configuration
	 */
	@Contribute(CoercionProvider.class)
	public static void contributeCoercionProvider(final Configuration<Coercion> configuration) {
		configuration.addInstance(BooleanToStringCoercion.class);
		configuration.addInstance(NumberToStringCoercion.class);
		configuration.addInstance(EnumToStringCoercion.class);
		configuration.addInstance(StringToBooleanCoercion.class);
		configuration.addInstance(StringToIntegerCoercion.class);
		configuration.addInstance(StringToDoubleCoercion.class);
	}

	@Contribute(ResourceLocator.class)
	public static void contributeResourceLocator(final OrderedConfiguration<ResourceProvider> configuration) {
		configuration.add(ClasspathResource.root(), ClasspathResource.ID);
		configuration.add(URIResource.root(), URIResource.ID);
	}
}
