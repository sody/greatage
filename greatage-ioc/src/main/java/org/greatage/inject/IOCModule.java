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

package org.greatage.inject;

import org.greatage.inject.access.ClassAccessSource;
import org.greatage.inject.access.ClassAccessSourceImpl;
import org.greatage.inject.annotations.Bind;
import org.greatage.inject.annotations.Contribute;
import org.greatage.inject.internal.coerce.BooleanToStringCoercion;
import org.greatage.inject.internal.coerce.DefaultCoercionProvider;
import org.greatage.inject.internal.coerce.EnumToStringCoercion;
import org.greatage.inject.internal.coerce.NumberToStringCoercion;
import org.greatage.inject.internal.coerce.StringToBooleanCoercion;
import org.greatage.inject.internal.coerce.StringToDoubleCoercion;
import org.greatage.inject.internal.coerce.StringToEnumCoercionProvider;
import org.greatage.inject.internal.coerce.StringToIntegerCoercion;
import org.greatage.inject.internal.coerce.TypeCoercerImpl;
import org.greatage.inject.internal.proxy.JavassistProxyFactory;
import org.greatage.inject.internal.resource.ClasspathResource;
import org.greatage.inject.internal.DefaultInjectionProvider;
import org.greatage.inject.internal.DefaultInjector;
import org.greatage.inject.internal.symbol.DefaultSymbolProvider;
import org.greatage.inject.internal.i18n.MessagesSourceImpl;
import org.greatage.inject.internal.scope.PrototypeScope;
import org.greatage.inject.internal.resource.ResourceLocatorImpl;
import org.greatage.inject.internal.scope.ScopeManagerImpl;
import org.greatage.inject.internal.symbol.SymbolSourceImpl;
import org.greatage.inject.internal.symbol.SystemSymbolProvider;
import org.greatage.inject.internal.scope.ThreadScope;
import org.greatage.inject.internal.resource.URIResource;
import org.greatage.inject.services.Coercion;
import org.greatage.inject.services.CoercionProvider;
import org.greatage.inject.services.InjectionProvider;
import org.greatage.inject.services.Injector;
import org.greatage.inject.internal.LoggerInjectionProvider;
import org.greatage.inject.internal.ServiceLocatorImpl;
import org.greatage.inject.internal.SymbolInjectionProvider;
import org.greatage.inject.services.ProxyFactory;
import org.greatage.inject.services.MessagesSource;
import org.greatage.inject.services.ResourceLocator;
import org.greatage.inject.services.ResourceProvider;
import org.greatage.inject.services.Scope;
import org.greatage.inject.services.ScopeManager;
import org.greatage.inject.services.SymbolProvider;
import org.greatage.inject.services.SymbolSource;
import org.greatage.inject.services.TypeCoercer;

/**
 * This class represents base module for Great Age IoC container that configures all needed core services. This are {@link
 * ProxyFactory}, {@link ScopeManager}, {@link SymbolSource}, {@link SymbolProvider}, {@link
 * ResourceLocator}, {@link MessagesSource}, {@link ClassAccessSource}.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class IOCModule {

	/**
	 * Binds all needed core services with their default implementations. This are {@link ProxyFactory}, {@link
	 * ScopeManager}, {@link SymbolSource}, {@link SymbolProvider}, {@link ResourceLocator}, {@link MessagesSource}, {@link
	 * ClassAccessSource}.
	 *
	 * @param binder service binder
	 */
	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(ProxyFactory.class, JavassistProxyFactory.class).eager();
		binder.bind(ScopeManager.class, ScopeManagerImpl.class).eager();

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
