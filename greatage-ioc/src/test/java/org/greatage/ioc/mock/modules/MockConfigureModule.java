/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.mock.modules;

import org.greatage.ioc.OrderedConfiguration;
import org.greatage.ioc.annotations.Build;
import org.greatage.ioc.annotations.Contribute;
import org.greatage.ioc.mock.MockMessageService;
import org.greatage.ioc.mock.MockMessageServiceImpl;
import org.greatage.ioc.mock.MockTalkService;
import org.greatage.ioc.mock.MockTalkServiceImpl;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockConfigureModule {

	@Build
	public MockMessageService buildMessageService(final List<String> messages) {
		return new MockMessageServiceImpl(messages);
	}

	@Build
	public MockTalkService buildTalkService(final MockMessageService messageService) {
		return new MockTalkServiceImpl(messageService);
	}

	@Contribute(MockMessageService.class)
	public void contributeMessageService(final OrderedConfiguration<String> configuration) {
		configuration.add("!!!", "end");
		configuration.add("hello", "hello");
		configuration.add("world", "world", "after:hello", "before:end");
	}
}

