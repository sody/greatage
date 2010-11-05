package org.greatage.mock;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockTalkServiceImpl implements MockTalkService {
	private final MockMessageService messageService;

	public MockTalkServiceImpl(final MockMessageService messageService) {
		this.messageService = messageService;
	}

	public String say() {
		return messageService.getMessage();
	}
}
