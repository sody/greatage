package org.greatage.mock;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockTalkServiceDelegate implements MockTalkService {
	private final MockTalkService delegate;
	private final String prefix;
	private final String suffix;

	public MockTalkServiceDelegate(final MockTalkService delegate, final String prefix, final String suffix) {
		this.delegate = delegate;
		this.prefix = prefix;
		this.suffix = suffix;
	}

	public String say() {
		return prefix + delegate.say() + suffix;
	}
}
