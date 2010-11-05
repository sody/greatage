package org.greatage.mock;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockInterfaceImpl1 implements MockInterface {
	private final String message;

	public MockInterfaceImpl1(final String message) {
		this.message = message;
	}

	public String say() {
		return message;
	}
}
