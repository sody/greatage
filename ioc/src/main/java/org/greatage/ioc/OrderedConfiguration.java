package org.greatage.ioc;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface OrderedConfiguration<V> {

	OrderedConfiguration<V> add(V item, String orderId, String... constraints);

	OrderedConfiguration<V> addInstance(Class<? extends V> itemClass, String orderId, String... constraints);

}
