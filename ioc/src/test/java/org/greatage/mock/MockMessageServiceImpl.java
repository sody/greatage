package org.greatage.mock;

import org.greatage.util.CollectionUtils;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockMessageServiceImpl implements MockMessageService {
	private final List<String> messages;
	private int messageNumber = 0;

	public MockMessageServiceImpl(final List<String> messages) {
		this.messages = messages;
	}

	public MockMessageServiceImpl(final String message) {
		this.messages = CollectionUtils.newList(message);
	}

	public String getMessage() {
		if (messageNumber >= messages.size()) {
			messageNumber = 0;
		}
		return messages.get(messageNumber++);
	}
}
