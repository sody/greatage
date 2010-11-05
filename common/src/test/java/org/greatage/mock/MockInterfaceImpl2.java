package org.greatage.mock;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockInterfaceImpl2 implements MockInterface {
	private final String message;

	public MockInterfaceImpl2() {
		this(null);
	}

	public MockInterfaceImpl2(final String message) {
		this.message = message;
	}

	public String say() {
		return message;
	}
}
