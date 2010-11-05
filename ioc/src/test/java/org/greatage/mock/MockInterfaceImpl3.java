package org.greatage.mock;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockInterfaceImpl3 implements MockInterface {
	public static final String MESSAGE = "mock-message";

	public String say() {
		return MESSAGE;
	}
}
