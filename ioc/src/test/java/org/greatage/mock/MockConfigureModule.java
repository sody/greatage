package org.greatage.mock;

import org.greatage.ioc.OrderedConfiguration;
import org.greatage.ioc.annotations.Build;
import org.greatage.ioc.annotations.Configure;

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

	@Configure(MockMessageService.class)
	public void configureMessageService(final OrderedConfiguration<String> configuration) {
		configuration.add("!!!", "end");
		configuration.add("hello", "hello");
		configuration.add("world", "world", "after:hello", "before:end");
	}
}

