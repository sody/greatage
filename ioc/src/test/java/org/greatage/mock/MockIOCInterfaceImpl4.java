/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.mock;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockIOCInterfaceImpl4 implements MockIOCInterface {
	private final MockIOCInterface delegate;

	public MockIOCInterfaceImpl4(final MockIOCInterface delegate) {
		this.delegate = delegate;
	}

	public String say(final String message) {
		return delegate.say(message);
	}

	public String say() {
		return delegate.say();
	}
}
