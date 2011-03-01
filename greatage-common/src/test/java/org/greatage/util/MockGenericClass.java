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
