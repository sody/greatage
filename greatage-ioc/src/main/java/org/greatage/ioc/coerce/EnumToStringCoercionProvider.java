package org.greatage.ioc.coerce;

import org.greatage.util.CollectionUtils;

import java.util.Map;

/**
 * This class represents {@link CoercionProvider} implementation that provides coercions for enum constants to string
 * coercion.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public class EnumToStringCoercionProvider extends AbstractCoercionProvider {
	private final Map<Class, Coercion> coercionsBySourceClass = CollectionUtils.newConcurrentMap();

	/**
	 * Creates new coercion provider instance that that provides coercions for enum constants to string coercion.
	 */
	public EnumToStringCoercionProvider() {
		super(Enum.class, String.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public <S, T> Coercion<S, T> getCoercion(final Class<S> sourceClass, final Class<T> targetClass) {
		if (!supports(sourceClass, targetClass)) {
			return null;
		}

		if (!coercionsBySourceClass.containsKey(sourceClass)) {
			coercionsBySourceClass.put(sourceClass, new EnumToStringCoercion(sourceClass));
		}
		return coercionsBySourceClass.get(sourceClass);
	}
}
