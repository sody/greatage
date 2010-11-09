/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.mock;

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
