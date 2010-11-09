/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.mock;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockProcessServiceDelegate implements MockProcessService {
	private final MockProcessService delegate;

	public MockProcessServiceDelegate(final MockProcessService delegate) {
		this.delegate = delegate;
	}

	public void process(final String message) {
		delegate.process(message);
	}
}
