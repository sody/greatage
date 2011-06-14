/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.app0;

import org.greatage.ioc.ServiceAdvice;
import org.greatage.ioc.annotations.Build;
import org.greatage.ioc.annotations.Intercept;
import org.greatage.ioc.annotations.Named;
import org.greatage.ioc.proxy.Interceptor;
import org.greatage.ioc.proxy.Invocation;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockInterceptModule {

	@Build
	public MockMessageService buildMessageService() {
		return new MockMessageServiceImpl("hello");
	}

	@Build
	@Named("talkService1")
	public MockTalkService buildTalkService(final MockMessageService messageService) {
		return new MockTalkServiceImpl(messageService);
	}

	@Build
	@Named("talkService2")
	public MockTalkService buildTalkService2(final MockMessageService messageService) {
		final MockTalkServiceImpl service = new MockTalkServiceImpl(messageService);
		return new MockTalkServiceDelegate(service, "[", "]");
	}

	@Intercept(MockTalkService.class)
	@Named("talkService2")
	public void interceptTalkService(final ServiceAdvice advice) {
		advice.add(new Interceptor() {
			public Object invoke(final Invocation invocation, final Object... parameters) throws Throwable {
				return "deprecated1:" + invocation.proceed(parameters);
			}
		}, "first");
	}

	@Intercept(MockTalkService.class)
	@Named("talkService2")
	public void interceptTalkService2(final ServiceAdvice advice) {
		advice.add(new Interceptor() {
			public Object invoke(final Invocation invocation, final Object... parameters) throws Throwable {
				return "deprecated2:" + invocation.proceed(parameters);
			}
		}, "second", "after:first");
	}
}
