package org.greatage.mock;

import org.greatage.util.Ordered;

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
