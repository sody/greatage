package org.greatage.util;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockGenericClass<T, E> {
	private final T value1;
	private final E value2;

	public MockGenericClass(final T value1, final E value2) {
		this.value1 = value1;
		this.value2 = value2;
	}

	public T getValue1() {
		return value1;
	}

	public E getValue2() {
		return value2;
	}
}
