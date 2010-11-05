package org.greatage.mock;

import org.greatage.ioc.ScopeConstants;
import org.greatage.ioc.ServiceBinder;
import org.greatage.ioc.annotations.Bind;
import org.greatage.ioc.annotations.Build;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockBindModule {

	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(MockTalkServiceImpl.class).withId("talkService1");
		binder.bind(MockTalkService.class, MockTalkServiceImpl.class).withId("talkService2");
	}

	@Build(scope = ScopeConstants.THREAD)
	public MockMessageService buildMessageService() {
		return new MockMessageServiceImpl("hello");
	}
}
