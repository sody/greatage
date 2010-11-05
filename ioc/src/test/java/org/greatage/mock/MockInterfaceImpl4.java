package org.greatage.mock;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockInterfaceImpl4 implements MockInterface {
	private final MockInterface delegate;

	public MockInterfaceImpl4(final MockInterface delegate) {
		this.delegate = delegate;
	}

	public String say() {
		return delegate.say();
	}
}
