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

import java.util.Arrays;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockOrderedObject implements Ordered {
	private final String orderId;
	private final List<String> orderConstraints;

	public MockOrderedObject(final String orderId, final String... orderConstraints) {
		this.orderId = orderId;
		this.orderConstraints = Arrays.asList(orderConstraints);
	}

	public String getOrderId() {
		return orderId;
	}

	public List<String> getOrderConstraints() {
		return orderConstraints;
	}
}
