/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.mock;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockIOCInterfaceImpl2 implements MockIOCInterface {
	private final String message;

	public MockIOCInterfaceImpl2() {
		this(null);
	}

	public MockIOCInterfaceImpl2(final String message) {
		this.message = message;
	}

	public String say(final String message) {
		return message;
	}

	public String say() {
		return message;
	}
}
