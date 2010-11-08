/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.mock;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockIOCInterfaceImpl3 implements MockIOCInterface {
	public static final String MESSAGE = "mock-message";

	public String say(final String message) {
		return message;
	}

	public String say() {
		return MESSAGE;
	}
}
