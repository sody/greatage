package org.greatage.ioc.coerce;

import org.greatage.util.CollectionUtils;

import java.util.Map;

/**
 * This class represents {@link CoercionProvider} implementation that provides coercions for string to enum constant
 * coercions.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public class StringToEnumCoercionProvider implements CoercionProvider {
	private final Map<Class, Coercion> coercionsByTargetClass = CollectionUtils.newConcurrentMap();

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public <S, T> Coercion<S, T> getCoercion(final Class<S> sourceClass, final Class<T> targetClass) {
		if (String.class.isAssignableFrom(sourceClass) && Enum.class.isAssignableFrom(targetClass)) {
			if (!coercionsByTargetClass.containsKey(targetClass)) {
				coercionsByTargetClass.put(targetClass, new StringToEnumCoercion(targetClass));
			}
			return coercionsByTargetClass.get(targetClass);
		}
		return null;
	}
}
