/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.mock.modules;

import org.greatage.ioc.ScopeConstants;
import org.greatage.ioc.ServiceBinder;
import org.greatage.ioc.annotations.Bind;
import org.greatage.ioc.annotations.Build;
import org.greatage.ioc.mock.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockBindModule {

	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(MockTalkServiceImpl1.class).withId("talkService1");
		binder.bind(MockTalkService.class, MockTalkServiceImpl.class).withId("talkService2");
	}

	@Build(scope = ScopeConstants.THREAD)
	public MockMessageService buildMessageService() {
		return new MockMessageServiceImpl("hello");
	}
}
