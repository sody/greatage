package org.greatage.ioc.coerce;

import org.greatage.util.CollectionUtils;
import org.greatage.util.MultiKey;

import java.util.Collection;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class TypeCoercerImpl implements TypeCoercer {
	private final Collection<Coercion> coercions;

	private final Map<MultiKey, Coercion> cache = CollectionUtils.newConcurrentMap();

	public TypeCoercerImpl(final Collection<Coercion> coercions) {
		assert coercions != null;

		this.coercions = coercions;
	}

	public <S, T> T coerce(final S input, final Class<T> targetClass) {
		assert targetClass != null;

		if (input == null || targetClass.isAssignableFrom(input.getClass())) {
			return targetClass.cast(input);
		}

		final Class<S> sourceClass = (Class<S>) input.getClass();

		final Coercion<S, T> coercion = getCoercion(sourceClass, targetClass);
		if (coercion == null) {
			throw new IllegalStateException(
					String.format("Can not find coercion from '%s' to '%s'", sourceClass, targetClass));
		}
		return coercion.coerce(input);
	}

	private <S, T> Coercion<S, T> getCoercion(final Class<S> sourceClass, final Class<T> targetClass) {
		final MultiKey key = new MultiKey(sourceClass, targetClass);
		if (cache.containsKey(key)) {
			return cache.get(key);
		}

		final Coercion<S, T> coercion = findCoercion(sourceClass, targetClass);
		cache.put(key, coercion);
		return coercion;
	}

	private <S, T> Coercion<S, T> findCoercion(final Class<S> sourceClass, final Class<T> targetClass) {
		for (Coercion coercion : coercions) {
			if (supports(coercion, sourceClass, targetClass)) {
				return coercion;
			}
		}
		return null;
	}

	private boolean supports(final Coercion coercion, final Class sourceClass, final Class targetClass) {
		if (sourceClass.isAssignableFrom(coercion.getSourceClass())) {
			if (targetClass.isAssignableFrom(coercion.getTargetClass())) {
				return true;
			}
		}
		return false;
	}
}
