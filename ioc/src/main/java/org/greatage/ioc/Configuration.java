package org.greatage.ioc;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Configuration<V> {

	Configuration<V> add(V item);

	Configuration<V> addInstance(Class<? extends V> itemClass);

}
