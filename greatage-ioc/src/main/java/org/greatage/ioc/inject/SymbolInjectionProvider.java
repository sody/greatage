package org.greatage.ioc.inject;

import org.greatage.ioc.InternalUtils;
import org.greatage.ioc.Marker;
import org.greatage.ioc.annotations.Symbol;
import org.greatage.ioc.coerce.TypeCoercer;
import org.greatage.ioc.symbol.SymbolSource;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SymbolInjectionProvider implements InjectionProvider {
	private final SymbolSource symbolSource;
	private final TypeCoercer typeCoercer;

	public SymbolInjectionProvider(final SymbolSource symbolSource, final TypeCoercer typeCoercer) {
		assert symbolSource != null;
		assert typeCoercer != null;

		this.symbolSource = symbolSource;
		this.typeCoercer = typeCoercer;
	}

	public <T> T inject(final Marker<?> marker, final Class<T> resourceClass, final Annotation... annotations) {
		final Symbol symbol = InternalUtils.findInArray(Symbol.class, annotations);
		if (symbol != null) {
			final String value = symbolSource.getValue(symbol.value());
			return typeCoercer.coerce(value, resourceClass);
		}

		return null;
	}
}
