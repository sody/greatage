package org.greatage.ioc;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface MappedConfiguration<K, V> {

	MappedConfiguration<K, V> add(K key, V value);

	MappedConfiguration<K, V> addInstance(K key, Class<? extends V> valueClass);

}
