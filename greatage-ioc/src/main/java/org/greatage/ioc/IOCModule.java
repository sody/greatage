/*
 * Copyright 2011 Ivan Khalopik
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
import org.greatage.ioc.coerce.BooleanToStringCoercion;
import org.greatage.ioc.coerce.Coercion;
import org.greatage.ioc.coerce.CoercionProvider;
import org.greatage.ioc.coerce.DefaultCoercionProvider;
import org.greatage.ioc.coerce.EnumToStringCoercion;
import org.greatage.ioc.coerce.NumberToStringCoercion;
import org.greatage.ioc.coerce.StringToBooleanCoercion;
import org.greatage.ioc.coerce.StringToDoubleCoercion;
import org.greatage.ioc.coerce.StringToEnumCoercionProvider;
import org.greatage.ioc.coerce.StringToIntegerCoercion;
import org.greatage.ioc.coerce.TypeCoercer;
import org.greatage.ioc.coerce.TypeCoercerImpl;
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
 * {@link ResourceLocator}, {@link MessagesSource}, {@link ClassAccessSource}.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class IOCModule {

	/**
	 * Binds all needed core services with their default implementations. This are {@link ProxyFactory}, {@link
	 * LoggerSource}, {@link ScopeManager}, {@link SymbolSource}, {@link SymbolProvider}, {@link ResourceLocator}, {@link
	 * MessagesSource}, {@link ClassAccessSource}.
	 *
	 * @param binder service binder
	 */
	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(ProxyFactory.class, JavassistProxyFactory.class);
		binder.bind(LoggerSource.class, Log4jLoggerSource.class);
		binder.bind(ScopeManager.class, ScopeManagerImpl.class);
		binder.bind(TypeCoercer.class, TypeCoercerImpl.class);
		binder.bind(CoercionProvider.class, DefaultCoercionProvider.class).withAlias(DefaultCoercionProvider.class);
		binder.bind(SymbolSource.class, SymbolSourceImpl.class);
		binder.bind(SymbolProvider.class, DefaultSymbolProvider.class).withAlias(DefaultSymbolProvider.class);

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
	@Contribute(DefaultCoercionProvider.class)
	public static void contributeCoercionProvider(final Configuration<Coercion> configuration) {
		configuration.addInstance(BooleanToStringCoercion.class);
		configuration.addInstance(NumberToStringCoercion.class);
		configuration.addInstance(EnumToStringCoercion.class);
		configuration.addInstance(StringToBooleanCoercion.class);
		configuration.addInstance(StringToIntegerCoercion.class);
		configuration.addInstance(StringToDoubleCoercion.class);
	}
}
