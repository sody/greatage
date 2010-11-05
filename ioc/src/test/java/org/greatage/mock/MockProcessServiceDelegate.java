package org.greatage.mock;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockProcessServiceDelegate implements MockProcessService {
	private final MockProcessService delegate;

	public MockProcessServiceDelegate(final MockProcessService delegate) {
		this.delegate = delegate;
	}

	public void process() {
		delegate.process();
	}
}
