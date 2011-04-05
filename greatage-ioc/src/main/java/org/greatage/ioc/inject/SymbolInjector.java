package org.greatage.ioc.inject;

import org.greatage.ioc.InternalUtils;
import org.greatage.ioc.Marker;
import org.greatage.ioc.annotations.Symbol;
import org.greatage.ioc.coerce.TypeCoercer;
import org.greatage.ioc.symbol.SymbolSource;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class SymbolInjector implements Injector {
	private final SymbolSource symbolSource;
	private final TypeCoercer typeCoercer;

	public SymbolInjector(final SymbolSource symbolSource, final TypeCoercer typeCoercer) {
		assert symbolSource != null;
		assert typeCoercer != null;

		this.symbolSource = symbolSource;
		this.typeCoercer = typeCoercer;
	}

	public <R, S> R inject(final Marker<S> marker, final Class<R> resourceClass, final Annotation... annotations) {
		final Symbol symbol = InternalUtils.findAnnotation(Symbol.class, annotations);
		if (symbol != null) {
			final String value = symbolSource.getValue(symbol.value());
			return typeCoercer.coerce(value, resourceClass);
		}

		return null;
	}
}
